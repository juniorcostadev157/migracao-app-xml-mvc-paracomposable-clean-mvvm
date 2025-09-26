package com.junior.migracaosevenmvvmcompose.domain.repository

import com.junior.migracaosevenmvvmcompose.domain.model.User


interface AuthRepository {

    suspend fun  login (email: String, password: String): Result<String>
    fun logout()
    fun isUserLoggedIn(): Boolean
    suspend fun verify2FA(userId: String, code: String): Result<Unit>
    suspend fun getUserData(userId: String): Result<User>

}