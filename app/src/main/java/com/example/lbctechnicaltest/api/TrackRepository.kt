package com.example.lbctechnicaltest.api

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

        /*var networkState = NetworkState.PENDING
        var albums: List<Album>

        withContext(Dispatchers.IO) {
            trackService.getAllTracks()
            trackDao.insertAll(trackService.getAllTracks())
        }
            .observeOn(schedulerProvider.ui())
            .subscribeOn(schedulerProvider.io())
            .subscribe({ response ->
                albums = buildAlbumList(response)

                schedulerProvider.io().scheduleDirect {
                    trackDao.deleteAll()
                    trackDao.insertAll(response)
                }

                Log.d(TAG, "getAlbums: SUCCESS")

                return@subscribe
            }) { throwable -> // Throw error
                throwable.printStackTrace()

                schedulerProvider.io().scheduleDirect {
                    albums.postValue(buildAlbumList(trackDao?.getAll() ?: emptyList()))
                }

                loaderVisible.postValue(false)

                networkState.postValue(NetworkState.ERROR)

                Log.e(TAG, "getAlbums: ERROR")
            }.dispose()*/
    }

    private fun handleNonHttpException(throwable: Throwable?) {
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