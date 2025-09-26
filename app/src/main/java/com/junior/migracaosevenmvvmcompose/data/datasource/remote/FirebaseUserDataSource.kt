package com.junior.migracaosevenmvvmcompose.data.datasource.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.junior.migracaosevenmvvmcompose.core.contants.Constants
import com.junior.migracaosevenmvvmcompose.data.model.UserResponse
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FirebaseUserDataSource @Inject constructor(private val firestore: FirebaseFirestore) {

    suspend fun getUserData(userId: String): Result<UserResponse>{
        return try {
            val snapshot = firestore.collection(Constants.FireBase.USERS_COLLECTION)
                .document(userId)
                .get()
                .await()

            val user = snapshot.toObject(UserResponse::class.java)
                ?: UserResponse()

            Result.success(user)
        }catch (e: Exception){
            Result.failure(e)
        }
    }
}