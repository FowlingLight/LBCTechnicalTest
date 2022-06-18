package com.example.lbctechnicaltest.models

import java.io.Serializable

data class Album(
    val id: Int,
    val trackList: MutableList<Track>
): Serializable