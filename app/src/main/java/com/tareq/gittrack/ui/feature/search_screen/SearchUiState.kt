package com.tareq.gittrack.ui.feature.search_screen

import com.tareq.gittrack.domain.model.GithubUser
import com.tareq.gittrack.domain.util.ErrorHandler


data class SearchUiState(
    val isLoading: Boolean = false,
    val isError: Boolean = false,
    val error: ErrorHandler? = null,
    val searchPlaceholderVisibility: Boolean = true,
    val isEmptySearchResult: Boolean = false,
    val searchTerm: String = "",
    val githubUsers: List<GithubUserUi> = emptyList(),
    val toast: Toast = Toast()
)

data class Toast(
    val isShow: Boolean = false,
    val message: String = ""
)

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
)

fun SearchUiState.screenContentVisibility() = !this.isLoading && !this.isError
fun GithubUser.toGithubUserUi(): GithubUserUi {
    return GithubUserUi(
        name = name,
        bio = bio,
        company = company,
        avatarUrl = avatarUrl,
        createdAt = createdAt,
        email = email,
        followers = followers,
        following = following,
        location = location
    )
}