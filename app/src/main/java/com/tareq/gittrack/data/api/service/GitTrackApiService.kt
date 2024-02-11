package com.tareq.gittrack.data.api.service

import com.tareq.gittrack.data.api.model.GithubUser
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface GitTrackApiService {
    @GET("users/{user}")
    suspend fun getGithubUser(@Path("user") user: String): Response<GithubUser>
}