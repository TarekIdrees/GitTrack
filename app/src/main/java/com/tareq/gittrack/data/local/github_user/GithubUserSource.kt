package com.tareq.gittrack.data.local.github_user

interface GithubUserSource {
    suspend fun insertGithubUser(
        id: Long,
        name: String,
        bio: String,
        company: String,
        avatarUrl: String,
        createdUrl: String,
        email: String,
        followers: String,
        following: String,
        location: String
    )
}