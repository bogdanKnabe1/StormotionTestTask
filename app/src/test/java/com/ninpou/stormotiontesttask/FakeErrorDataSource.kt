package com.ninpou.stormotiontesttask

import com.ninpou.stormotiontesttask.data.network.OperationResult
import com.ninpou.stormotiontesttask.model.Data
import com.ninpou.stormotiontesttask.model.DataVideo
import com.ninpou.stormotiontesttask.model.SuggestionItemDataSourceI

class FakeErrorDataSource : SuggestionItemDataSourceI {

    private val mockException = Exception("Erorr")

    override suspend fun retrieveData(): OperationResult<Data> {
        return OperationResult.Error(mockException)
    }

    override suspend fun retrieveVideoData(): OperationResult<DataVideo> {
        return OperationResult.Error(mockException)
    }
}