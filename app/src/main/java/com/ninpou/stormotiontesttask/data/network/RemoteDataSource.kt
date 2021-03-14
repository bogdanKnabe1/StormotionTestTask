package com.ninpou.stormotiontesttask.data.network

import com.ninpou.stormotiontesttask.model.Data
import com.ninpou.stormotiontesttask.model.SuggestionItemDataSourceI

// make network request to request data and retrieve it, using interface and API(retrofit),
// then process result with OperationResult class, depending on the response, we will retrieve data or generate an error
class RemoteDataSource(apiClient: ApiClient) : SuggestionItemDataSourceI {

    private val service = apiClient.build()

    override suspend fun retrieveData(): OperationResult<Data> {
        try {
            val response = service?.dataList()
            response?.let {
                return run {
                    val data = it
                    OperationResult.Success(data)
                }
            } ?: run {
                return OperationResult.Error(Exception("An error occurred"))
            }
        } catch (e: Exception) {
            return OperationResult.Error(e)
        }
    }
}