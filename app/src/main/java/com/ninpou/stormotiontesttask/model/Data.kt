package com.ninpou.stormotiontesttask.model

import java.io.Serializable

data class Data(
    val headerTitle: String,
    val id: Int,
    val image: String,
    val subTitle: String,
    val title: String,
    val type: Int
) : Serializable
