package com.tareq.gittrack.domain.usecase

import com.tareq.gittrack.domain.model.GithubUser
import com.tareq.gittrack.domain.repository.GitTrackRepository
import javax.inject.Inject

 class InsertGithubUserIntoDatabaseUseCase @Inject constructor(
    private val gitTrackRepository: GitTrackRepository
) {
    suspend operator fun invoke(user: GithubUser) {
        gitTrackRepository.insertGithubUserInDatabase(user)
    }
}