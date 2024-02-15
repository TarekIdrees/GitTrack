package com.tareq.gittrack.data.repository


import com.tareq.gittrack.data.api.mapper.toGithubUser
import com.tareq.gittrack.data.api.model.GithubSearchResponse
import com.tareq.gittrack.data.api.service.GitTrackApiService
import com.tareq.gittrack.domain.model.GithubUser
import com.tareq.gittrack.domain.repository.GitTrackRepository
import com.tareq.gittrack.domain.util.ForbiddenException
import com.tareq.gittrack.domain.util.InternalServerException
import com.tareq.gittrack.domain.util.InvalidDataException
import com.tareq.gittrack.domain.util.NoConnectionException
import com.tareq.gittrack.domain.util.NotFoundException
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject


class GitTrackRepositoryImpl @Inject constructor(
    private val gitTrackApiService: GitTrackApiService
) : GitTrackRepository {
    override suspend fun searchGithubUser(
        user: String
    ): Flow<GithubUser> =
        wrap(gitTrackApiService.searchGithubUser(user)).map {
            it.toGithubUser()
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun searchGithubUsers(searchTerm: String)
            : Flow<List<GithubUser>> {
        return wrap(gitTrackApiService.searchGithubUsers(searchTerm)).flatMapConcat { githubUsersGeneralInformation ->
            githubUsersGeneralInformation.githubUsers
                ?.filterNot {
                    it.login.isNullOrBlank()
                }?.filter {
                    it.login!!.contains(searchTerm.trim(), ignoreCase = true)
                }?.map {
                    searchGithubUser(it.login!!)
                }.let { flows ->
                    combine(flows!!.asIterable()) { entities ->
                        entities.toList()
                    }
                }
        }
    }


    private suspend fun <T> wrap(function: Response<T>): Flow<T> {
        return flow {
            if (function.isSuccessful) {
                val body = function.body()
                if (body is GithubSearchResponse) {
                    if (body.totalCount == 0) {
                        throw NotFoundException()
                    }
                }
                function.body()?.let { emit(it) } ?: throw NotFoundException()
            } else {
                when (function.code()) {
                    502 -> throw NoConnectionException()
                    400 -> throw InvalidDataException()
                    403 -> throw ForbiddenException()
                    404 -> throw NotFoundException()
                    500 -> throw InternalServerException()
                    else -> {
                        throw Exception()
                    }
                }
            }
        }
    }
}