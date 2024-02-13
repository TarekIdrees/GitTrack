package com.tareq.gittrack.domain.usecase

import com.tareq.gittrack.data.api.repository.GitTrackRepository
import com.tareq.gittrack.domain.model.GithubUserEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapConcat
import javax.inject.Inject

class SearchGithubUsersUseCase @Inject constructor(
    private val searchGithubUserUseCase: SearchGithubUserUseCase,
    private val gitTrackRepository: GitTrackRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    suspend operator fun invoke(searchTerm: String): Flow<List<GithubUserEntity>> {
        return gitTrackRepository.searchGithubUsers(searchTerm)
            .flatMapConcat { githubUsersGeneralInformation ->
                githubUsersGeneralInformation
                    .filterNot {
                        it.login.isNullOrBlank()
                    }.filter {
                        it.login!!.contains(searchTerm.trim(),ignoreCase = true)
                    }
                    .sortedBy {
                        it.followersUrl
                    }
                    .map { githubUserGeneralInformation ->
                        searchGithubUserUseCase(githubUserGeneralInformation.login!!)
                    }
                    .toList()
                    .let { flows ->
                        combine(flows) { entities ->
                            entities.toList()
                        }
                    }
            }
    }
}
