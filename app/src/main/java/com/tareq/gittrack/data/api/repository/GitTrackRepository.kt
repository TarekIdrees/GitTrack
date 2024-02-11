package com.tareq.gittrack.data.api.repository

import android.util.Log
import com.tareq.gittrack.data.api.model.GithubUser
import com.tareq.gittrack.data.api.service.GitTrackApiService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GitTrackRepository {
    suspend fun getGithubUser(user: String): Flow<GithubUser>
}

class GitTrackRepositoryImpl @Inject constructor(
    private val gitTrackApiService: GitTrackApiService
) : GitTrackRepository {
    override suspend fun getGithubUser(
        user: String
    ): Flow<GithubUser> =
        flow {
            val response = gitTrackApiService.getGithubUser(user)
            try {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    Log.d("GitTrackRepository", "Github user is: ${responseBody?.name}")
                    responseBody?.let {
                        emit(it)
                    }
                } else {
                    Log.d("GitTrackRepository", "Github user error: ${response.code()}")
                }
            } catch (e: Exception) {
                Log.d("GitTrackRepository", "Github user exception: ${e.message}")
            }
        }
}