package com.junior.migracaosevenmvvmcompose.domain.usecase.users

import com.junior.migracaosevenmvvmcompose.domain.repository.UsersLocalDataSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GetUserLoginFromPreferencesUseCase @Inject constructor(
    private val userLocalDataSource: UsersLocalDataSource
) {
    operator fun invoke(): Flow<String?>{
        return userLocalDataSource.getUserSession().map {session->
            session?.login
        }
    }
}