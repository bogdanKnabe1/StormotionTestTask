package com.ninpou.stormotiontesttask.data.network

import com.ninpou.stormotiontesttask.model.Data

// data class for holding data of response ( status code, message, and fields of response )
data class DataResponse(val status: Int?, val msg: String?, val data: List<Data>?)