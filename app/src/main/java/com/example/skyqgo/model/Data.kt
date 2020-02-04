package com.example.skyqgo.model


import com.google.gson.annotations.SerializedName

data class Data(
    val genre: String,
    val id: Int,
    val poster: String,
    val title: String,
    val year: String
)