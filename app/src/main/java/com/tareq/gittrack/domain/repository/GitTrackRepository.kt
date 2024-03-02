package com.tareq.gittrack.domain.repository

import com.tareq.gittrack.domain.model.GithubUser
import kotlinx.coroutines.flow.Flow

interface GitTrackRepository {
    // Api functions
    fun searchGithubUser(user: String): Flow<GithubUser>
    fun searchGithubUsers(searchTerm: String): Flow<List<GithubUser>>

    // Database functions
    suspend fun insertGithubUserInDatabase(user: GithubUser)
    fun getMatchedGithubUsersFromDatabase(userName: String): Flow<List<GithubUser>>
}