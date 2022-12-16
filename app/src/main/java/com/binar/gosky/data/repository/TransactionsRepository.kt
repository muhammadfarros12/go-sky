package com.binar.gosky.data.repository

import com.binar.gosky.data.network.datasource.TransactionsRemoteDataSource
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionRequestBody
import com.binar.gosky.data.network.model.transactions.new_transaction.NewTransactionResponse
import com.binar.gosky.wrapper.Resource
import javax.inject.Inject

interface TransactionsRepository {
    suspend fun postNewTransaction(accessToken: String, newTransactionRequestBody: NewTransactionRequestBody
    ): Resource<NewTransactionResponse>
}

class TransactionsRepositoryImpl @Inject constructor(private val dataSource: TransactionsRemoteDataSource): TransactionsRepository {
    override suspend fun postNewTransaction(
        accessToken: String,
        newTransactionRequestBody: NewTransactionRequestBody
    ): Resource<NewTransactionResponse> {
        return proceed {
            dataSource.postNewTransaction(accessToken, newTransactionRequestBody)
        }
    }

    private suspend fun <T> proceed(coroutines: suspend () -> T): Resource<T> {
        return try {
            Resource.Success(coroutines.invoke())
        } catch (e: Exception) {
            Resource.Error(e)
        }
    }

}