package com.ninpou.stormotiontesttask.data.network

import com.ninpou.stormotiontesttask.model.SuggestionItem
import com.ninpou.stormotiontesttask.model.SuggestionItemDataSourceI

// make network request to request data and retrieve it, using interface and API(retrofit),
// then process result with OperationResult class, depending on the response, we will retrieve data or generate an error
class SuggestionItemRemoteDataSource(apiClient: ApiClient) : SuggestionItemDataSourceI {

    private val service = apiClient.build()

    override suspend fun retrieveListData(): OperationResult<SuggestionItem> {
        try {
            val response = service?.listOfItems()
            response?.let {
                return if (it.isSuccessful && it.body() != null) {
                    val data = it.body()?.data
                    OperationResult.Success(data)
                } else {
                    val message = it.body()?.msg
                    OperationResult.Error(Exception(message))
                }
            } ?: run {
                return OperationResult.Error(Exception("An error has occurred!"))
            }
        } catch (e: Exception) {
            return OperationResult.Error(e)
        }
    }
}