package com.example.lbctechnicaltest.api

import com.example.lbctechnicaltest.models.Track
import io.reactivex.Single
import retrofit2.http.GET

interface AlbumService {
    @GET("img/shared/technical-test.json")
    fun getAllTracks(): Single<List<Track>>
}