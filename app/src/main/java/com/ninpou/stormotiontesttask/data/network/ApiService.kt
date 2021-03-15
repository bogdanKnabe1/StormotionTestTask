package com.ninpou.stormotiontesttask.data.network

import com.ninpou.stormotiontesttask.model.Data
import com.ninpou.stormotiontesttask.model.DataVideo
import retrofit2.http.GET

// TEST API
interface ApiService {
    @GET("/data?key=fd233120")
    suspend fun dataList(): List<Data>

    @GET("/video_api?key=fd233120")
    suspend fun dataVideo(): List<DataVideo>
}