package com.binar.gosky.di

import com.binar.gosky.data.repository.*
import com.binar.gosky.data.repository.TicketsRepository
import com.binar.gosky.data.repository.TicketsRepositoryImpl
import com.binar.gosky.data.repository.TransactionsRepository
import com.binar.gosky.data.repository.TransactionsRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun provideTicketsRepository(ticketsRepositoryImpl: TicketsRepositoryImpl): TicketsRepository

    @Binds
    abstract fun provideAuthRepository(authRepositoryImpl: AuthRepositoryImpl): AuthRepository

    @Binds
    abstract fun provideUserRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository

    @Binds
    abstract fun provideTransactionsRepository(transactionsRepositoryImpl: TransactionsRepositoryImpl): TransactionsRepository
}