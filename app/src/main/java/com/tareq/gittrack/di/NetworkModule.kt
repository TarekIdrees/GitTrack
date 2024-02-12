package com.tareq.gittrack.di

import com.tareq.gittrack.data.api.interceptors.StatusCodeInterceptor
import com.tareq.gittrack.data.api.service.GitTrackApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    @Provides
    @Singleton
    fun provideOkhttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addNetworkInterceptor(HttpLoggingInterceptor())
            .addInterceptor(StatusCodeInterceptor())
            .build()
    }

    @Provides
    @Singleton
    fun provideGitTrackApiService(okHttpClient: OkHttpClient): GitTrackApiService {
        val retrofit = Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        return retrofit.create(GitTrackApiService::class.java)
    }
}