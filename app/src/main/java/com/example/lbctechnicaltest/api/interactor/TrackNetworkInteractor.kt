package com.example.lbctechnicaltest.api.interactor

import com.example.lbctechnicaltest.api.*
import com.example.lbctechnicaltest.models.Track
import com.example.lbctechnicaltest.utils.BaseSchedulerProvider
import io.reactivex.Single

class TrackNetworkInteractor(
    private val memoryInteractor: TrackMemoryInteractor,
    private val databaseInteractor: TrackDatabaseInteractor,
    private val trackService: TrackService,
    private val schedulerProvider: BaseSchedulerProvider,
) {
    fun getTracks(): Single<List<Track>> {
        return trackService.getAllTracks()
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .doOnSuccess(databaseInteractor::saveData)
            .doOnSuccess(memoryInteractor::saveData)
    }
}