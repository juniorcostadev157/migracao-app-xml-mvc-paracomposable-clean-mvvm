package com.junior.migracaosevenmvvmcompose.data.datasource.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.junior.migracaosevenmvvmcompose.core.contants.Constants
import com.junior.migracaosevenmvvmcompose.data.model.FerramentalTecnicoResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import javax.inject.Inject

class FirebaseFerramentalDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {

    fun  listFerramental(login: String): Flow<List<FerramentalTecnicoResponse>> = callbackFlow{

        val listener  = firestore.collection(Constants.FireBase.FERRAMENTAL_TECNICO_COLLECTION)
            .whereEqualTo("Login", login)
            .addSnapshotListener { snapshot, error ->
                if (error !=null){
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot !=null){
                    val list = snapshot.documents.mapNotNull { doc->
                        doc.toObject(FerramentalTecnicoResponse::class.java)?.apply { id = doc.id }


                    }
                    trySend(list).isSuccess
                }

            }

        awaitClose { listener.remove() }

    }
}