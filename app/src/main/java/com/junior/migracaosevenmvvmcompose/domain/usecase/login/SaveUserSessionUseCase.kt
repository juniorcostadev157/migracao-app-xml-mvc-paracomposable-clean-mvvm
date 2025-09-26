package com.junior.migracaosevenmvvmcompose.domain.usecase.login


import com.junior.migracaosevenmvvmcompose.data.model.UserSession
import com.junior.migracaosevenmvvmcompose.domain.model.User
import com.junior.migracaosevenmvvmcompose.domain.repository.UsersLocalDataSource
import javax.inject.Inject

class SaveUserSessionUseCase @Inject constructor(private val userLocalDataSource: UsersLocalDataSource) {
    suspend operator fun invoke(user: User){

        val session = UserSession(
            userId = user.uid,
            nome = user.nome,
            login = user.login,
            profileImageUrl = user.profileImageUrl,
            tipo = user.tipo
                    )
        userLocalDataSource.saveUserSession(session)
    }
}