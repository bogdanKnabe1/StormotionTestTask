package com.ninpou.stormotiontesttask.model

import com.ninpou.stormotiontesttask.data.network.OperationResult

interface SuggestionItemDataSourceI {
    suspend fun retrieveListData(): OperationResult<SuggestionItem>
}