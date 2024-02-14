package com.tareq.gittrack.domain.usecase

import com.tareq.gittrack.data.api.model.GithubUserResponse
import com.tareq.gittrack.data.api.repository.GitTrackRepository
import com.tareq.gittrack.domain.model.GithubUser
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchGithubUserUseCase @Inject constructor(
    private val gitTrackRepository: GitTrackRepository
) {
    suspend operator fun invoke(
        user: String
    ) = gitTrackRepository.searchGithubUser(user).map {
        it.toGithubUserEntity()
    }

    private fun GithubUserResponse.toGithubUserEntity(): GithubUser {
        return GithubUser(
            name = login ?: "Undefined",
            bio = bio ?: "Undefined",
            company = company ?: "Undefined",
            avatarUrl = avatarUrl ?: "Undefined",
            createdAt = createdAt?.substringBefore("T") ?: "Undefined",
            email = email?.toString() ?: "Undefined",
            followers = followers?.toString() ?: "Undefined",
            following = following?.toString() ?: "Undefined",
            location = location ?: "Undefined"
        )
    }
}