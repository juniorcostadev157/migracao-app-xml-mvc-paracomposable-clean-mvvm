package com.junior.migracaosevenmvvmcompose.domain.usecase.login

import com.junior.migracaosevenmvvmcompose.data.datasource.local.UsersPreference
import com.junior.migracaosevenmvvmcompose.domain.repository.AuthRepository
import com.junior.migracaosevenmvvmcompose.domain.repository.UsersLocalDataSource
import javax.inject.Inject

class LogoutUseCase @Inject constructor(
    private val repository: AuthRepository,
    private val usersLocalDataSource: UsersLocalDataSource
) {
    suspend operator fun invoke(){
        repository.logout()
        usersLocalDataSource.clear()
    }
}