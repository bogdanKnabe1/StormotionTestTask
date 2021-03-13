package com.ninpou.stormotiontesttask.data.network

import android.util.Log
import com.ninpou.stormotiontesttask.model.Data
import com.ninpou.stormotiontesttask.model.SuggestionItemDataSourceI

// make network request to request data and retrieve it, using interface and API(retrofit),
// then process result with OperationResult class, depending on the response, we will retrieve data or generate an error
class RemoteDataSource(apiClient: ApiClient) : SuggestionItemDataSourceI {

    private val service = apiClient.build()

    override suspend fun retrieveData(): OperationResult<Data> {
        try {
            val response = service?.museums()
            response?.let {
                return if (it.isSuccessful && it.body() != null) {
                    val data = it.body()?.data
                    Log.d("ASD", "${it.body()}")
                    OperationResult.Success(data)
                } else {
                    val message = it.body()?.msg
                    OperationResult.Error(Exception(message))
                }
            } ?: run {
                return OperationResult.Error(Exception("An error occurred"))
            }
        } catch (e: Exception) {
            return OperationResult.Error(e)
        }
    }
}