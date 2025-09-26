package com.junior.migracaosevenmvvmcompose.domain.usecase.login

import com.junior.migracaosevenmvvmcompose.domain.repository.UsersLocalDataSource
import com.junior.migracaosevenmvvmcompose.presentation.navigation.LoginStage
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ObserveLoginStageUseCase @Inject constructor(
    private val localDataSource: UsersLocalDataSource
) {
    operator fun invoke(): Flow<LoginStage>{
        return localDataSource.getUserSession().map { session ->
            when{
                session == null -> LoginStage.LoggedOut
                session.userId?.isEmpty() ?: true -> LoginStage.LoggedOut
                else-> LoginStage.Authenticated
            }
        }
    }
}