package com.junior.migracaosevenmvvmcompose.di

import com.junior.migracaosevenmvvmcompose.data.datasource.local.UsersPreference
import com.junior.migracaosevenmvvmcompose.domain.repository.AuthRepository
import com.junior.migracaosevenmvvmcompose.data.repository.remote.AuthRepositoryImpl
import com.junior.migracaosevenmvvmcompose.domain.repository.EquipamentosRepository
import com.junior.migracaosevenmvvmcompose.data.repository.remote.EquipamentosRepositoryImpl
import com.junior.migracaosevenmvvmcompose.domain.repository.FerramentalRepository
import com.junior.migracaosevenmvvmcompose.data.repository.remote.FerramentalRepositoryImpl
import com.junior.migracaosevenmvvmcompose.domain.repository.SolicitacaoRepository
import com.junior.migracaosevenmvvmcompose.data.repository.remote.SolicitacaoRepositoryImpl
import com.junior.migracaosevenmvvmcompose.domain.repository.UsersLocalDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: AuthRepositoryImpl): AuthRepository

    @Binds
    @Singleton
    abstract fun bindSolicitacaoRepository(impl: SolicitacaoRepositoryImpl): SolicitacaoRepository

    @Binds
    @Singleton
    abstract fun bindEquipamentosRepository(impl: EquipamentosRepositoryImpl): EquipamentosRepository

    @Binds
    @Singleton
    abstract fun bindFerramentalRepository(impl: FerramentalRepositoryImpl): FerramentalRepository

    @Binds
    @Singleton
    abstract fun bindUserLocalDataSource(impl: UsersPreference): UsersLocalDataSource
}