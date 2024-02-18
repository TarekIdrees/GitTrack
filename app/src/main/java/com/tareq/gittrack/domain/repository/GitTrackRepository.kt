package com.tareq.gittrack.domain.repository

import com.tareq.gittrack.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GitTrackRepository {
    // Api functions
    suspend fun searchGithubUser(user: String): Flow<GithubUser>
    suspend fun searchGithubUsers(searchTerm: String): Flow<List<GithubUser>>

    // Database functions
    suspend fun insertGithubUserInDatabase(user: GithubUser)
    suspend fun getMatchedGithubUsersFromDatabase(userName: String): Flow<List<GithubUser>>
}