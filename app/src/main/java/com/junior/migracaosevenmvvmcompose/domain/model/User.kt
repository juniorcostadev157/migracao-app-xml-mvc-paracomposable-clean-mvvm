package com.junior.migracaosevenmvvmcompose.domain.model

import com.junior.migracaosevenmvvmcompose.data.model.UserResponse

data class User(
    val email: String,
    val login: String,
    val nome: String,
    val telefone: String,
    val tipo: String,
    val uid: String,
    val currentTotp: String,
    val fcmToken: String,
    val profileImageUrl: String,
    val totpUsed: Boolean
)
    fun UserResponse.toDomain() = User(
        email = this.email,
        login = this.login,
        nome = this.nome,
        telefone = this.telefone,
        tipo = this.tipo,
        uid = this.uid,
        currentTotp = this.currentTotp,
        fcmToken = this.fcmToken,
        profileImageUrl = this.profileImageUrl,
        totpUsed = this.totpUsed
    )
