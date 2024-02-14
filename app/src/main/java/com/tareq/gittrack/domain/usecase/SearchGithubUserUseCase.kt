package com.tareq.gittrack.domain.usecase


import com.tareq.gittrack.domain.repository.GitTrackRepository
import com.tareq.gittrack.domain.util.handelGithubUserFields
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchGithubUserUseCase @Inject constructor(
    private val gitTrackRepository: GitTrackRepository
) {
    suspend operator fun invoke(
        user: String
    ) = gitTrackRepository.searchGithubUser(user).map {
        it.handelGithubUserFields()
    }
}