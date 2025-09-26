package com.junior.migracaosevenmvvmcompose.di

import android.content.Context
import com.junior.migracaosevenmvvmcompose.data.datasource.local.UsersPreference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserPreferenceModule {
    @Provides
    @Singleton
    fun provideUsersPreference(@ApplicationContext context: Context): UsersPreference {
        return UsersPreference(context)

    }
}