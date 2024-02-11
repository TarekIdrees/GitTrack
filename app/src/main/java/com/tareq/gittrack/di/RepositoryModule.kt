package com.tareq.gittrack.di

import com.tareq.gittrack.data.api.repository.GitTrackRepository
import com.tareq.gittrack.data.api.repository.GitTrackRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Singleton
    @Binds
    abstract fun bindGitTrackRepository(repository: GitTrackRepositoryImpl): GitTrackRepository
}