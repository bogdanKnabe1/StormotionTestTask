package com.ninpou.stormotiontesttask.model

import com.ninpou.stormotiontesttask.data.network.OperationResult

// repository class make mapping between network requests and ViewModel (map data from network to viewmodel)
class SuggestionItemRepository(private val dataSource: SuggestionItemDataSourceI) {

    suspend fun fetchListOfData(): OperationResult<SuggestionItem> = dataSource.retrieveListData()
}