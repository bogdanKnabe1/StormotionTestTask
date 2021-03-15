package com.ninpou.stormotiontesttask.model

import com.ninpou.stormotiontesttask.data.network.OperationResult

interface SuggestionItemDataSourceI {
    suspend fun retrieveData(): OperationResult<Data>
    suspend fun retrieveVideoData(): OperationResult<DataVideo>
}