package com.tareq.gittrack.domain.usecase

import com.tareq.gittrack.data.api.model.GithubUserResponse
import com.tareq.gittrack.data.api.repository.GitTrackRepository
import com.tareq.gittrack.domain.model.GithubUserEntity
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

    private fun GithubUserResponse.toGithubUserEntity(): GithubUserEntity {
        return GithubUserEntity(
            name = name ?: "",
            bio = bio ?: "",
            company = company ?: "",
            avatarUrl = avatarUrl ?: "",
            createdAt = createdAt ?: "",
            email = email.toString(),
            followers = followers.toString(),
            following = following.toString(),
            location = location ?: ""
        )
    }
}