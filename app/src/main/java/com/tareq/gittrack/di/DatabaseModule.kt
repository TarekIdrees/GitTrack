package com.tareq.gittrack.di

import android.app.Application
import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.tareq.gittrack.GHUsersDatabase
import com.tareq.gittrack.data.local.github_user.GithubUserSource
import com.tareq.gittrack.data.local.github_user.GithubUserSourceImpl
import com.tareq.gittrack.data.local.github_users.GithubUsersSource
import com.tareq.gittrack.data.local.github_users.GithubUsersSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideSqlDriver(app: Application): SqlDriver {
        return AndroidSqliteDriver(
            schema = GHUsersDatabase.Schema,
            context = app,
            name = "GHUsers.db"
        )
    }

    @Provides
    @Singleton
    fun provideGithubUserSource(driver: SqlDriver): GithubUserSource {
        return GithubUserSourceImpl(GHUsersDatabase(driver))
    }


    @Provides
    @Singleton
    fun provideGithubUsersSource(driver: SqlDriver): GithubUsersSource {
        return GithubUsersSourceImpl(GHUsersDatabase(driver))
    }

}