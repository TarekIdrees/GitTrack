package com.tareq.gittrack.domain.usecase

import com.tareq.gittrack.domain.model.GithubUser
import com.tareq.gittrack.domain.repository.GitTrackRepository
import com.tareq.gittrack.domain.util.handelGithubUserFields
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchGithubUsersOfflineUseCase @Inject constructor(
    private val gitTrackRepository: GitTrackRepository
) {
    suspend operator fun invoke(searchTerm: String): Flow<List<GithubUser>> {
        return gitTrackRepository.getMatchedGithubUsersFromDatabase(searchTerm).map { githubUsers ->
            githubUsers.map { githubUser ->
                githubUser.handelGithubUserFields()
            }
        }
    }
}