package com.example.lbctechnicaltest.models

import com.google.gson.annotations.SerializedName

data class Track(
    val albumId: Int,
    val id: Int,
    val title: String,
    @SerializedName("url")
    val pictureUrl: String,
    val thumbnailUrl: String
)