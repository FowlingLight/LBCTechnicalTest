package com.example.lbctechnicaltest.models

import androidx.room.*
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Track(
    @PrimaryKey val id: Int,
    @ColumnInfo(name = "album_id") val albumId: Int,
    @ColumnInfo(name = "title") val title: String,
    @SerializedName("url")
    @ColumnInfo(name = "picture_url") val pictureUrl: String,
    @ColumnInfo(name = "thumbnail_url") val thumbnailUrl: String
): Serializable