package com.ninpou.stormotiontesttask.data.network

import com.ninpou.stormotiontesttask.model.SuggestionItem

// data class for holding data of response ( status code, message, and fields of response )
data class SuggestionResponse(val status: Int?, val msg: String?, val data: List<SuggestionItem>?) {
    fun isSuccess(): Boolean = (status == 200)
}