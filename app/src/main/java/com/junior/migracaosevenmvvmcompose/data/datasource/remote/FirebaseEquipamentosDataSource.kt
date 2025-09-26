package com.junior.migracaosevenmvvmcompose.data.datasource.remote


import com.google.firebase.firestore.FirebaseFirestore
import com.junior.migracaosevenmvvmcompose.core.contants.Constants
import com.junior.migracaosevenmvvmcompose.data.model.EquipamentoResponse
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow

class FirebaseEquipamentosDataSource @Inject constructor(
    private val firestore: FirebaseFirestore
) {
    fun listEquipamentos(login: String): Flow<List<EquipamentoResponse>> = callbackFlow{
    val listener = firestore.collection(Constants.FireBase.EQUIPAMENTOS_COLLECTION)
        .whereEqualTo("Destino", login)
        .addSnapshotListener { snapshot, error ->
            if (error != null){
                close(error)
                return@addSnapshotListener
            }
            if (snapshot !=null){
                val list = snapshot.documents.mapNotNull {doc->

                    doc.toObject(EquipamentoResponse::class.java)?.apply {id = doc.id}  }
                trySend(list).isSuccess
                }
            }

        awaitClose { listener.remove() }
        }

}