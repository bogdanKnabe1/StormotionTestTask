package com.ninpou.stormotiontesttask.model

import java.io.Serializable

//class SuggestionItem : ArrayList<SuggestionItemItem>()

data class SuggestionItem(
    val id: Int,
    val image: String,
    val subTitle: String,
    val title: String,
    val type: String
) : Serializable
