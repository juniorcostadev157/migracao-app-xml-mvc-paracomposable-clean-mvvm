package com.junior.migracaosevenmvvmcompose.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class SolicitacaoEquipamentosResponse(
    var id: String = "",
    var equipamentos: List<EquipamentosSolicitacaoResponse> = listOf(),
    @get:PropertyName("id_almoxarife") @set:PropertyName("id_almoxarife")
     var idAlmoxarife: String? = null,
    @get:PropertyName("login_tecnico") @set:PropertyName("login_tecnico")
     var loginTecnico: String? = null,
    @get:PropertyName("nome_almoxarife") @set:PropertyName("nome_almoxarife")
     var nomeAlmoxarife: String? = null,
    var status: String? = null,
    var data: Timestamp? = null

)

data class EquipamentosSolicitacaoResponse(
    @get:PropertyName("NumeroSerie") @set:PropertyName("NumeroSerie")
    var numeroSerie: String = "",
    @get:PropertyName("Tipo") @set:PropertyName("Tipo")
    var tipo: String = "",
)
