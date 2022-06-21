package com.example.lbctechnicaltest.api.interactor

import com.example.lbctechnicaltest.database.TrackDao
import com.example.lbctechnicaltest.models.Track
import com.example.lbctechnicaltest.utils.BaseSchedulerProvider
import io.reactivex.*

class TrackDatabaseInteractor(
    private val trackDao: TrackDao,
    private val memoryInteractor: TrackMemoryInteractor,
    private val schedulerProvider: BaseSchedulerProvider
) {

    fun getTracks(): Maybe<List<Track>> {
        return trackDao.getAll()
            .subscribeOn(schedulerProvider.io())
            .doOnSuccess(memoryInteractor::saveData)
    }

    fun saveData(tracks: List<Track>) {
        Single.just {
            trackDao.deleteAll()
            trackDao.insertAll(tracks)
        }.subscribeOn(schedulerProvider.io())
    }
}