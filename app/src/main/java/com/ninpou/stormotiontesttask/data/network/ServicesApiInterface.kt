package com.ninpou.stormotiontesttask.data.network

import retrofit2.Response
import retrofit2.http.GET

//make network calls
interface ServicesApiInterface {
    @GET("/mock_api")
    suspend fun listOfItems(): Response<SuggestionResponse>
}