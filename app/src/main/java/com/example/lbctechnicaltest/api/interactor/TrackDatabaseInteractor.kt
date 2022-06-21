package com.example.lbctechnicaltest.api.interactor

import com.example.lbctechnicaltest.database.AppDatabase
import com.example.lbctechnicaltest.models.Track
import com.example.lbctechnicaltest.utils.BaseSchedulerProvider
import io.reactivex.*

class TrackDatabaseInteractor(
    private val database: AppDatabase,
    private val memoryInteractor: TrackMemoryInteractor,
    private val schedulerProvider: BaseSchedulerProvider
) {

    fun getTracks(): Maybe<List<Track>> {
        return database.trackDao().getAll()
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .doOnSuccess(memoryInteractor::saveData)
    }

    fun saveData(tracks: List<Track>) {
        Single.just {
            database.trackDao().deleteAll()
            database.trackDao().insertAll(tracks)
        }.observeOn(schedulerProvider.ui()).subscribeOn(schedulerProvider.io())
    }
}