package com.junior.migracaosevenmvvmcompose.domain.usecase.login

import com.junior.migracaosevenmvvmcompose.domain.repository.AuthRepository
import javax.inject.Inject

class Verify2FAUseCase @Inject constructor(private val authRepository: AuthRepository){

    suspend operator fun invoke(userId: String, code: String): Login2FAResult {

        if (code.isBlank()) return Login2FAResult.EmptyFields

        return authRepository.verify2FA(userId, code).fold(
            onSuccess = { Login2FAResult.Success},
            onFailure = { Login2FAResult.Wrong2FACode}
        )
    }
}