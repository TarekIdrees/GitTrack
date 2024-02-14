package com.tareq.gittrack.data.local.github_users

import gittrack.githubuserdb.GithubUserEntity
import kotlinx.coroutines.flow.Flow

interface GithubUsersSource {
    fun getMatchedUsersByName(name: String): Flow<List<GithubUserEntity>>
}