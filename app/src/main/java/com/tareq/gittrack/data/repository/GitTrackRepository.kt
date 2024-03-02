package com.tareq.gittrack.data.repository


import com.tareq.gittrack.data.api.mapper.toGithubUser
import com.tareq.gittrack.data.api.service.GitTrackApiService
import com.tareq.gittrack.data.local.github_user.GithubUserSourceImpl
import com.tareq.gittrack.data.local.toGithubUser
import com.tareq.gittrack.data.local.toGithubUserEntity
import com.tareq.gittrack.domain.model.GithubUser
import com.tareq.gittrack.domain.repository.GitTrackRepository
import com.tareq.gittrack.domain.util.ErrorHandler
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emitAll
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import java.net.UnknownHostException
import javax.inject.Inject


class GitTrackRepositoryImpl @Inject constructor(
    private val gitTrackApiService: GitTrackApiService,
    private val githubUserSourceImpl: GithubUserSourceImpl,
) : GitTrackRepository {

    override fun searchGithubUser(
        user: String
    ): Flow<GithubUser> {
        return flow {
            val githubUser = wrap(gitTrackApiService.searchGithubUser(user)).toGithubUser()
            // Caching the data into the database
            insertGithubUserInDatabase(githubUser)
            emit(githubUser)
        }
    }


    override fun searchGithubUsers(searchTerm: String): Flow<List<GithubUser>> {
        val users = mutableListOf<GithubUser>()
        return flow {
            try {
                wrap(gitTrackApiService.searchGithubUsers(searchTerm)).githubUsers
                    ?.apply {
                        if (this.isEmpty()) emptyFlow<List<GithubUser>>()
                    }!!.forEach {
                        it.login.takeUnless { login ->
                            login.isNullOrBlank()
                        }.also { login ->
                            searchGithubUser(login!!).first { githubUser ->
                                users.add(githubUser)
                            }
                        }
                    }
                emit(users)
            } catch (e: Exception) {
                if (
                    e is ErrorHandler.Forbidden ||
                    e is ErrorHandler.NoConnection ||
                    e is ErrorHandler.UnKnownError ||
                    e is UnknownHostException
                ) {
                    // Get cached data
                    try {
                        val githubUsers = getMatchedGithubUsersFromDatabase(searchTerm)
                        emitAll(githubUsers)
                    } catch (databaseError: Exception) {
                        if (e is ErrorHandler.Forbidden)
                            throw ErrorHandler.ForbiddenAndNotFoundOffline
                        else
                            throw databaseError
                    }
                } else {
                    throw e
                }
            }
        }
    }


    override suspend fun insertGithubUserInDatabase(user: GithubUser) {
        githubUserSourceImpl.insertGithubUser(user.toGithubUserEntity())
    }

    override fun getMatchedGithubUsersFromDatabase(userName: String): Flow<List<GithubUser>> {
        return githubUserSourceImpl.getMatchedUsersByName(userName)
            .map {
                it.map { githubUserEntity ->
                    githubUserEntity.toGithubUser()
                }
            }
    }


    private fun <T> wrap(function: Response<T>): T {
        return try {
            if (function.isSuccessful) {
                function.body() ?: throw ErrorHandler.NotFound
            } else {
                when (function.code()) {
                    502 -> throw ErrorHandler.NoConnection
                    400 -> throw ErrorHandler.InvalidData
                    403 -> throw ErrorHandler.Forbidden
                    404 -> throw ErrorHandler.NotFound
                    500 -> throw ErrorHandler.InternalServer
                    else -> throw ErrorHandler.UnKnownError
                }
            }
        } catch (error: Exception) {
            throw ErrorHandler.UnKnownError
        }
    }
}