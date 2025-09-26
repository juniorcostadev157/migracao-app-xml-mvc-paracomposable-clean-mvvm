package com.junior.migracaosevenmvvmcompose.data.repository.remote

import com.bastiaanjansen.otp.TOTP
import com.junior.migracaosevenmvvmcompose.data.datasource.remote.FirebaseAuthDataSource
import com.junior.migracaosevenmvvmcompose.data.datasource.remote.FirebaseUserDataSource
import com.junior.migracaosevenmvvmcompose.domain.model.User
import com.junior.migracaosevenmvvmcompose.domain.model.toDomain
import com.junior.migracaosevenmvvmcompose.domain.repository.AuthRepository

import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authDataSource: FirebaseAuthDataSource,
    private val userDataSource: FirebaseUserDataSource): AuthRepository {

    override suspend fun login(
        email: String,
        password: String
    ): Result<String> {
        return  authDataSource.login(email, password)
    }

    override fun logout() {
        authDataSource.logout()
    }

    override fun isUserLoggedIn(): Boolean {
       return authDataSource.isUserLoggedIn()
    }

    override suspend fun verify2FA(
        userId: String,
        code: String
    ): Result<Unit> {
        return try {
            val totpSecret = userDataSource.getUserData(userId).getOrThrow().currentTotp
            val totp = TOTP.Builder(totpSecret.toByteArray(Charsets.UTF_8)).build()
            if (totp.verify(code)){
                Result.success(Unit)
            }else{
                Result.failure(Exception("Código incorreto"))
            }
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    override suspend fun getUserData(userId: String): Result<User> {
        return userDataSource.getUserData(userId).map { it.toDomain() }
    }
}