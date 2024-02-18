package com.tareq.gittrack.data.local.github_user

import gittrack.githubuserdb.GithubUserEntity
import kotlinx.coroutines.flow.Flow

interface GithubUserSource {
    suspend fun insertGithubUser(githubUser: GithubUserEntity)
    fun getMatchedUsersByName(name: String): Flow<List<GithubUserEntity>>
}