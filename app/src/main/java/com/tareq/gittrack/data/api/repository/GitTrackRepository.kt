package com.tareq.gittrack.data.api.repository


import com.tareq.gittrack.data.api.model.GithubGeneralUserInformationResponse
import com.tareq.gittrack.data.api.model.GithubUserResponse
import com.tareq.gittrack.data.api.service.GitTrackApiService
import com.tareq.gittrack.data.api.util.NotFoundException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import retrofit2.Response
import javax.inject.Inject

interface GitTrackRepository {
    suspend fun searchGithubUser(user: String): Flow<GithubUserResponse>
    suspend fun searchGithubUsers(searchTerm: String): Flow<List<GithubGeneralUserInformationResponse>>
}

class GitTrackRepositoryImpl @Inject constructor(
    private val gitTrackApiService: GitTrackApiService
) : GitTrackRepository {
    override suspend fun searchGithubUser(
        user: String
    ): Flow<GithubUserResponse> =
        wrap(gitTrackApiService.searchGithubUser(user))

    override suspend fun searchGithubUsers(searchTerm: String)
    : Flow<List<GithubGeneralUserInformationResponse>> {
        return wrap(gitTrackApiService.searchGithubUsers(searchTerm)).map { githubSearchResponse ->
            githubSearchResponse.githubUsers.orEmpty().filterNotNull()
        }
    }


    private suspend fun <T> wrap(function: Response<T>): Flow<T> {
        return flow {
            if (function.isSuccessful) {
                function.body()?.let { emit(it) } ?: throw NotFoundException()
            }
        }
    }
}