package com.tareq.gittrack.data.api.mapper

import com.tareq.gittrack.data.api.model.GithubUserResponse
import com.tareq.gittrack.domain.model.GithubUser

fun GithubUserResponse.toGithubUser(): GithubUser {
    return GithubUser(
        name = login ?: "",
        bio = bio ?: "",
        company = company ?: "",
        avatarUrl = avatarUrl ?: "",
        createdAt = createdAt ?: "",
        email = email?.toString() ?: "",
        followers = followers?.toString() ?: "",
        following = following?.toString() ?: "",
        location = location ?: "",
        link = if (login.isNullOrBlank()) "" else "https://github.com/$login"
    )
}