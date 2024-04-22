package com.tareq.gittrack.di

import android.app.Application
import android.content.Context
import com.tareq.gittrack.ui.util.StringDictionary
import com.tareq.gittrack.ui.util.StringResources
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object StringDictionaryModule {
    @Module
    @InstallIn(SingletonComponent::class)
    object StringResourceModule {
        @Singleton
        @Provides
        fun provideStringResource(context: Context): StringDictionary {
            return StringResources(context)
        }
    }

    @Module
    @InstallIn(SingletonComponent::class)
    object ContextModule {
        @Singleton
        @Provides
        fun provideContext(application: Application): Context {
            return application.applicationContext
        }
    }
}