package com.ninpou.stormotiontesttask.model

import java.io.Serializable

/*
data class Data(
    val id: Int,
    val name: String,
    val photo: String
) : Serializable*/

data class Data(
    val id: Int,
    val image: String,
    val subTitle: String,
    val title: String,
    val type: String
)