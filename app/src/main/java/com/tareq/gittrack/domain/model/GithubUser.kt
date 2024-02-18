package com.tareq.gittrack.domain.model

data class GithubUser(
    val id: String,
    val name: String,
    val bio: String,
    val company: String,
    val avatarUrl: String,
    val createdAt: String,
    val email: String,
    val followers: String,
    val following: String,
    val location: String,
    val link: String,
)
