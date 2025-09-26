package com.junior.migracaosevenmvvmcompose.data.model

import com.google.firebase.Timestamp
import com.google.firebase.firestore.PropertyName

data class SolicitacaoFerramentalResponse(

    var id: String = "",
    @get: PropertyName("Data_envio") @set:PropertyName("Data_envio")
    var dataEnvio: Timestamp? = null,
    @get: PropertyName("Ferramental") @set:PropertyName("Ferramental")
    var ferramental: List<FerramentasResponseList> = emptyList(),
    @get: PropertyName("LoginTecnico") @set:PropertyName("LoginTecnico")
    var loginTecnico: String? = null,
    @get: PropertyName("NomeAlmox") @set:PropertyName("NomeAlmox")
    var nomeAlmox: String? = null,
    @get: PropertyName("NomeTecnico") @set:PropertyName("NomeTecnico")
    var nomeTecnico: String? = null,
    @get: PropertyName("Status") @set:PropertyName("Status")
    var status: String? = null

)

data class FerramentasResponseList(
    val dataValidade: String? = null,
    val nomeFerramenta: String? = null,
    val validade: String? = null,
    val valor: String? = null
)