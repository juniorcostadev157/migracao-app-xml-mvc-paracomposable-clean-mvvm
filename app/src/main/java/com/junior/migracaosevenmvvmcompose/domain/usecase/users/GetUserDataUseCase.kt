package com.junior.migracaosevenmvvmcompose.domain.usecase.users

import com.junior.migracaosevenmvvmcompose.domain.repository.AuthRepository
import javax.inject.Inject

class GetUserDataUseCase @Inject constructor(private val repository: AuthRepository) {

    suspend operator fun invoke(userId: String) = repository.getUserData(userId)

}