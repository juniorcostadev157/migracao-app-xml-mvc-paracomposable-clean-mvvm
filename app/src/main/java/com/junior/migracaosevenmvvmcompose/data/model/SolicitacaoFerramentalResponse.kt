package com.junior.migracaosevenmvvmcompose.data.model

import com.google.firebase.Timestamp

data class SolicitacaoFerramentalResponse(

    var id: String = "",
    val Data_envio: Timestamp? = null,
    val Ferramental: List<FerramentasModel?> = emptyList(),
    val LoginTecnico: String? = null,
    val NomeAlmox: String? = null,
    val NomeTecnico: String? = null,
    val Status: String? = null

)

data class FerramentasModel(
    val dataValidade: String? = null,
    val nomeFerramenta: String? = null,
    val validade: String? = null,
    val valor: String? = null
)