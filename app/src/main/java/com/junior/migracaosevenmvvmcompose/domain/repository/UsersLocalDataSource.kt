package com.junior.migracaosevenmvvmcompose.domain.repository

import com.junior.migracaosevenmvvmcompose.data.model.UserSession
import kotlinx.coroutines.flow.Flow

interface UsersLocalDataSource {
    suspend fun saveUserSession(session: UserSession)
    fun getUserSession(): Flow<UserSession?>
    suspend fun clear()

}