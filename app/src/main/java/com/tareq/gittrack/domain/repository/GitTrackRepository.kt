package com.tareq.gittrack.domain.repository

import com.tareq.gittrack.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GitTrackRepository {
    suspend fun searchGithubUser(user: String): Flow<GithubUser>
    suspend fun searchGithubUsers(searchTerm: String): Flow<List<GithubUser>>
}