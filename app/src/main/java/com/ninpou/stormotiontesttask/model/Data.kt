package com.ninpou.stormotiontesttask.model

import java.io.Serializable


data class Data(
    val id: Int,
    val name: String,
    val photo: String
) : Serializable