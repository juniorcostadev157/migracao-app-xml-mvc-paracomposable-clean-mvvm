package com.junior.migracaosevenmvvmcompose.data.usecase.ferramental

import com.junior.migracaosevenmvvmcompose.data.model.FerramentalTecnicoModel
import com.junior.migracaosevenmvvmcompose.domain.repository.FerramentalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListFerramentalUseCase @Inject constructor(
    private val repository: FerramentalRepository
) {

    operator fun invoke(login: String): Flow<List<FerramentalTecnicoModel>>{
        return repository.listFerramental(login)
    }
}