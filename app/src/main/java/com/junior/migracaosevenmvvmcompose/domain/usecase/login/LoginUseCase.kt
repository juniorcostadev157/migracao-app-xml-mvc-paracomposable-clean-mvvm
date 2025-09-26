package com.junior.migracaosevenmvvmcompose.domain.usecase.login

import android.util.Log
import com.google.firebase.FirebaseNetworkException

import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthInvalidUserException
import com.junior.migracaosevenmvvmcompose.domain.repository.AuthRepository
import javax.inject.Inject


class LoginUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(email: String, password: String): LoginResult {




        return try {
           val userId =  repository.login(email, password).getOrThrow()
            LoginResult.Success(userId)

        }catch (e: FirebaseAuthInvalidUserException){
            Log.d("LoginUseCase", "invoke: ${e.message}")
            LoginResult.UserNotFound

        }catch (e: FirebaseAuthInvalidCredentialsException){
            Log.d("LoginUseCase", "invoke: ${e.message}")
            LoginResult.WrongPassword

        }catch (e: FirebaseNetworkException){
            Log.d("LoginUseCase", "invoke: ${e.message}")
            LoginResult.NetworkError

        }catch (e: Exception){
            LoginResult.UnknownError(e.message)
        }
    }






}