package com.tareq.gittrack.domain.util

import com.tareq.gittrack.domain.model.GithubUser

fun GithubUser.handelGithubUserFields(): GithubUser {
    return GithubUser(
        name = name.ifBlank { "Undefined" },
        bio = bio.ifBlank { "Undefined" },
        company = company.ifBlank { "Undefined" },
        avatarUrl = avatarUrl.ifBlank { "Undefined" },
        createdAt = if (createdAt.isBlank()) "Undefined" else createdAt.substringBefore("T"),
        email = email.ifBlank { "Undefined" },
        followers = followers.ifBlank { "Undefined" },
        following = following.ifBlank { "Undefined" },
        location = location.ifBlank { "Undefined" }
    )
}