package com.emedinaa.kotlinmvvm

import com.ninpou.stormotiontesttask.data.network.OperationResult
import com.ninpou.stormotiontesttask.model.Data
import com.ninpou.stormotiontesttask.model.DataVideo
import com.ninpou.stormotiontesttask.model.SuggestionItemDataSourceI


class FakeEmptyDataSource : SuggestionItemDataSourceI {

    override suspend fun retrieveData(): OperationResult<Data> {
        return OperationResult.Success(emptyList())
    }

    override suspend fun retrieveVideoData(): OperationResult<DataVideo> {
        return OperationResult.Success(emptyList())
    }
}