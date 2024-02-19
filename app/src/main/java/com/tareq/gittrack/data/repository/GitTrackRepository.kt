package com.tareq.gittrack.data.repository


import com.tareq.gittrack.data.api.mapper.toGithubUser
import com.tareq.gittrack.data.api.service.GitTrackApiService
import com.tareq.gittrack.data.local.github_user.GithubUserSourceImpl
import com.tareq.gittrack.data.local.toGithubUser
import com.tareq.gittrack.data.local.toGithubUserEntity
import com.tareq.gittrack.domain.model.GithubUser
import com.tareq.gittrack.domain.repository.GitTrackRepository
import com.tareq.gittrack.domain.util.ForbiddenException
import com.tareq.gittrack.domain.util.InternalServerException
import com.tareq.gittrack.domain.util.InvalidDataException
import com.tareq.gittrack.domain.util.NoConnectionException
import com.tareq.gittrack.domain.util.NotFoundException
import com.tareq.gittrack.domain.util.NotFoundOfflineException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEmpty
import retrofit2.Response
import javax.inject.Inject


class GitTrackRepositoryImpl @Inject constructor(
    private val gitTrackApiService: GitTrackApiService,
    private val githubUserSourceImpl: GithubUserSourceImpl,
) : GitTrackRepository {

    override suspend fun searchGithubUser(
        user: String
    ): Flow<GithubUser> {
        val githubUser = wrap(gitTrackApiService.searchGithubUser(user)).toGithubUser()
        return flow { emit(githubUser) }
    }


    override suspend fun searchGithubUsers(searchTerm: String): Flow<List<GithubUser>> {
        val users = mutableListOf<GithubUser>()
        wrap(gitTrackApiService.searchGithubUsers(searchTerm)).githubUsers
            ?.apply {
                if (this.isEmpty()) throw NotFoundException()
            }!!.forEach {
                it.login.takeUnless { login ->
                    login.isNullOrBlank()
                }.also { login ->
                    searchGithubUser(login!!).first { githubUser ->
                        users.add(githubUser)
                    }
                }
            }
        return flow { emit(users) }
    }


    override suspend fun insertGithubUserInDatabase(user: GithubUser) {
        githubUserSourceImpl.insertGithubUser(user.toGithubUserEntity())
    }

    override suspend fun getMatchedGithubUsersFromDatabase(userName: String): Flow<List<GithubUser>> {
        return githubUserSourceImpl.getMatchedUsersByName(userName)
            .onEmpty { throw NotFoundOfflineException() }
            .map {
                it.map { githubUserEntity ->
                    githubUserEntity.toGithubUser()
                }
            }
    }


    private fun <T> wrap(function: Response<T>): T {
        return try {
            if (function.isSuccessful) {
                function.body() ?: throw NotFoundException()
            } else {
                when (function.code()) {
                    502 -> throw NoConnectionException()
                    400 -> throw InvalidDataException()
                    403 -> throw ForbiddenException()
                    404 -> throw NotFoundException()
                    500 -> throw InternalServerException()
                    else -> throw Exception()
                }
            }
        } catch (e: Exception) {
            when (function.code()) {
                502 -> throw NoConnectionException()
                400 -> throw InvalidDataException()
                403 -> throw ForbiddenException()
                404 -> throw NotFoundException()
                500 -> throw InternalServerException()
                else -> throw Exception()
            }
        }
    }
}