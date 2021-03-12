package com.ninpou.strormotiontesttask.model

class SuggestionItem : ArrayList<SuggestionItemItem>()

data class SuggestionItemItem(
    val id: Int,
    val image: String,
    val subTitle: String,
    val title: String,
    val type: String
)
