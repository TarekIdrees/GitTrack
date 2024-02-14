package com.tareq.gittrack.data.local.github_user

import com.tareq.gittrack.GHUsersDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class GithubUserSourceImpl(
    githubUsersDatabase: GHUsersDatabase
) : GithubUserSource {

    private val queries = githubUsersDatabase.githubUserEntityQueries
    override suspend fun insertGithubUser(
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
    ) {
        withContext(Dispatchers.IO) {
            queries.insertGithubUser(
                github_user_id = id,
                name = name,
                bio = bio,
                company = company,
                avatar_url = avatarUrl,
                created_url = createdUrl,
                email = email,
                followers = followers,
                following = following,
                location = location
            )
        }
    }
}