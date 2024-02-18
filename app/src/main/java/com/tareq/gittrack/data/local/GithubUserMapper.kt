package com.tareq.gittrack.data.local

import com.tareq.gittrack.domain.model.GithubUser
import gittrack.githubuserdb.GithubUserEntity

fun GithubUser.toGithubUserEntity(): GithubUserEntity {
    return GithubUserEntity(
        github_user_id = id.toLong(),
        name = name,
        bio = bio,
        company = company,
        avatar_url = avatarUrl,
        created_url = createdAt,
        email = email,
        followers = followers,
        following = following,
        location = location,
        url = link
    )
}

fun GithubUserEntity.toGithubUser(): GithubUser {
    return GithubUser(
        id = github_user_id.toString(),
        name = name,
        bio = bio,
        company = company,
        avatarUrl = avatar_url,
        createdAt = created_url,
        email = email,
        followers = followers,
        following = following,
        location = location,
        link = url
    )
}