package com.example.lbctechnicaltest.api

import android.util.Log
import com.example.lbctechnicaltest.api.interactor.*
import com.example.lbctechnicaltest.database.AppDatabase
import com.example.lbctechnicaltest.models.Track
import com.example.lbctechnicaltest.utils.*
import com.google.gson.JsonSyntaxException
import io.reactivex.Observable
import io.reactivex.disposables.Disposable
import retrofit2.HttpException
import java.net.*

class TrackRepository(
    database: AppDatabase,
    trackService: TrackService,
    private val schedulerProvider: BaseSchedulerProvider
) {
    companion object {
        const val TAG = "TrackRepository"
    }

    private var memoryInteractor = TrackMemoryInteractor()
    private var databaseInteractor = TrackDatabaseInteractor(database, memoryInteractor, schedulerProvider)
    private var networkInteractor =
        TrackNetworkInteractor(memoryInteractor, databaseInteractor, trackService, schedulerProvider)

    fun getAllTracks(disposableList: MutableList<Disposable>): Observable<List<Track>> {
        Log.v(TAG, "getAllTracks")

        disposableList.add(
            Observable.concat(
                memoryInteractor.getTracks().toObservable(),
                databaseInteractor.getTracks().toObservable(),
                networkInteractor.getTracks().toObservable()
            )
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .doOnError(this::handleNonHttpException)
                .subscribe()
        )

        return memoryInteractor.tracksObservable
    }

    private fun handleNonHttpException(throwable: Throwable?) {
        Log.v(TAG, "handleNonHttpException")

        when (throwable) {
            is HttpException, is JsonSyntaxException, is SocketTimeoutException, is ConnectException -> {
                throw LBCException()
            }
            else -> {
                throwable?.printStackTrace()
                throw RuntimeException(throwable)
            }
        }
    }
}