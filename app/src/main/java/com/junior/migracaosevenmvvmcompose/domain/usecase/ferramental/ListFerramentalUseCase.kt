package com.junior.migracaosevenmvvmcompose.domain.usecase.ferramental


import com.junior.migracaosevenmvvmcompose.domain.model.FerramentalTecnico
import com.junior.migracaosevenmvvmcompose.domain.repository.FerramentalRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListFerramentalUseCase @Inject constructor(
    private val repository: FerramentalRepository
) {

    operator fun invoke(login: String): Flow<List<FerramentalTecnico>>{
        return repository.listFerramental(login)
    }
}