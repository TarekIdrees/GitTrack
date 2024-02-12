package com.tareq.gittrack.data.api.service

import com.tareq.gittrack.data.api.model.GithubSearchResponse
import com.tareq.gittrack.data.api.model.GithubUserResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitTrackApiService {
    @GET("users/{user}")
    suspend fun searchGithubUser(@Path("user") user: String): Response<GithubUserResponse>
    @GET("search/users")
    suspend fun searchGithubUsers(@Query("q") query: String): Response<GithubSearchResponse>
}