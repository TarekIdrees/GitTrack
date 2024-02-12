package com.tareq.gittrack.domain.usecase

import com.tareq.gittrack.data.api.repository.GitTrackRepository
import com.tareq.gittrack.domain.model.GithubUserEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.toList
import javax.inject.Inject

class SearchGithubUsersUseCase @Inject constructor(
    private val searchGithubUserUseCase: SearchGithubUserUseCase,
    private val gitTrackRepository: GitTrackRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(searchTerm: String): Flow<List<GithubUserEntity>> {
        return gitTrackRepository.searchGithubUsers(searchTerm)
            .flatMapConcat { githubUsersGeneralInformation ->
                githubUsersGeneralInformation.take(2).map { githubUserGeneralInformation ->
                    searchGithubUserUseCase(githubUserGeneralInformation.login ?: "").toList()
                }.asFlow()
            }
    }
}
