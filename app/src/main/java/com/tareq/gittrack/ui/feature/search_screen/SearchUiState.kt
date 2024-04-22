package com.tareq.gittrack.ui.feature.search_screen

import com.tareq.gittrack.domain.model.GithubUser


sealed interface SearchUiState {
    data object Loading : SearchUiState

    data object LoadFailed: SearchUiState

    data object SearchPlaceholder : SearchUiState

    data class Success(
        val githubUsers: List<GithubUserUi> = emptyList(),
    ) : SearchUiState {
        fun isEmpty(): Boolean = githubUsers.isEmpty()
    }
}

data class GithubUserUi(
    val name: String = "",
    val bio: String = "",
    val company: String = "",
    val avatarUrl: String = "",
    val createdAt: String = "",
    val email: String = "",
    val followers: String = "",
    val following: String = "",
    val location: String = "",
    val link: String = ""
)

internal fun GithubUser.toGithubUserUi(): GithubUserUi {
    return GithubUserUi(
        name = userName,
        bio = bio,
        company = company,
        avatarUrl = avatarUrl,
        createdAt = createdAt,
        email = email,
        followers = followers,
        following = following,
        location = location,
        link = link
    )
}