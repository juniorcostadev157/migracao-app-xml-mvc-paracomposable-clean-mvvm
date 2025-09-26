package com.junior.migracaosevenmvvmcompose.data.datasource.remote

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ListenerRegistration
import com.junior.migracaosevenmvvmcompose.core.contants.Constants
import com.junior.migracaosevenmvvmcompose.data.model.SolicitacaoCheckInResponse
import com.junior.migracaosevenmvvmcompose.data.model.SolicitacaoEquipamentosResponse
import com.junior.migracaosevenmvvmcompose.data.model.SolicitacaoFerramentalResponse
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class FireBaseSolicitacaoDataSource @Inject  constructor(
    private val  firestore: FirebaseFirestore
) {

   fun listSolicitacoesEquipamentos(login: String): Flow<List<SolicitacaoEquipamentosResponse>> = callbackFlow{

        val listener: ListenerRegistration = firestore.collection(Constants.FireBase.SOLICITACOES_COLLECTION)
            .whereEqualTo("login_tecnico", login)
            .whereEqualTo("status", "Pendente")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot !=null){
                    val list = snapshot.documents.mapNotNull { doc->
                        doc.toObject(SolicitacaoEquipamentosResponse::class.java)?.apply { id = doc.id }
                    }
                    trySend(list).isSuccess
                }
            }

       awaitClose { listener.remove() }
    }

    fun listSolicitacaoFerramental(login: String): Flow<List<SolicitacaoFerramentalResponse>> = callbackFlow{
        val listener: ListenerRegistration = firestore.collection(Constants.FireBase.SOLICITACOES_FERRAMENTAL_COLLECTION)
            .whereEqualTo("LoginTecnico", login)
            .whereEqualTo("Status", "PENDENTE")
            .addSnapshotListener { snapshot, error ->
                if (error != null){
                    close(error)
                    return@addSnapshotListener
                }

                if (snapshot != null){
                    val list = snapshot.documents.mapNotNull { doc->
                        doc.toObject(SolicitacaoFerramentalResponse::class.java)?.apply { id = doc.id }
                    }
                    trySend(list).isSuccess
                }

            }
        awaitClose { listener.remove() }
    }

    fun listSolicitacaoCheckIn(login: String): Flow<List<SolicitacaoCheckInResponse>> = callbackFlow{
        val listener: ListenerRegistration = firestore.collection(Constants.FireBase.SOLICITACOES_CHECKIN_COLLECTION)
            .whereEqualTo("LoginTecnico", login)
            .whereEqualTo("Status", "PENDENTE")
            .addSnapshotListener { snapshot, error ->
                if (error != null){
                    close(error)
                    return@addSnapshotListener
                }
                if (snapshot != null){
                    val list = snapshot.documents.mapNotNull { doc->
                        doc.toObject(SolicitacaoCheckInResponse::class.java)?.apply { id = doc.id }
                    }
                    trySend(list).isSuccess
                }
            }
        awaitClose { listener.remove() }

    }

    suspend fun updateStatusSolicitacao(id: String, status: String){
        firestore.collection(Constants.FireBase.SOLICITACOES_COLLECTION).document(id)
            .update("status", status).await()

    }

    suspend fun updateStatusSolicitacaoFerramental(id: String, status: String){
        firestore.collection(Constants.FireBase.SOLICITACOES_FERRAMENTAL_COLLECTION).document(id)
            .update("Status", status).await()
    }

    suspend fun updateStatusSolicitacaoCheckIn(id: String, status: String){
        firestore.collection(Constants.FireBase.SOLICITACOES_CHECKIN_COLLECTION).document(id)
            .update("Status", status).await()
    }
}