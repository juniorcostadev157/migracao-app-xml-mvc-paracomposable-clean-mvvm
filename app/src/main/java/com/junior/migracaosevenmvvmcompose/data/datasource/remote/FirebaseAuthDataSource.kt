package com.junior.migracaosevenmvvmcompose.data.datasource.remote


import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseAuthDataSource @Inject constructor(private val firebaseAuth: FirebaseAuth) {

    suspend fun login(email: String, password: String): Result<String>{
        return try {
            val result  = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            val userId = result.user?.uid ?: return Result.failure(Exception("User ID is null"))
            Result.success(userId)
        }catch (e: Exception){
            Result.failure(e)
        }
    }

    fun logout (){
        firebaseAuth.signOut()
    }

    fun isUserLoggedIn(): Boolean{
        return firebaseAuth.currentUser != null
    }
}