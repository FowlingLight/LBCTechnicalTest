package com.example.lbctechnicaltest.viewmodels

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.lbctechnicaltest.api.*
import com.example.lbctechnicaltest.models.*
import com.example.lbctechnicaltest.models.utils.NetworkState
import com.example.lbctechnicaltest.utils.*

class AlbumListViewModel(
    private val albumService: AlbumService = RetrofitAPIClient.albumService,
    private val schedulerProvider: BaseSchedulerProvider = SchedulerProvider()
) : BaseViewModel() {
    companion object {
        const val TAG = "AlbumListViewModel"
    }

    val albums = MutableLiveData<List<Album>>()

    fun getAlbums() {
        Log.v(TAG, "getAlbums")

        loaderVisible.value = true

        networkState.postValue(NetworkState.PENDING)
        disposableList.add(
            albumService.getAllTracks()
                .observeOn(schedulerProvider.ui())
                .subscribeOn(schedulerProvider.io())
                .subscribe({ response ->
                    albums.postValue(buildAlbumList(response))
                    loaderVisible.postValue(false)
                    networkState.postValue(NetworkState.SUCCESS)

                    Log.d(TAG, "getAlbums: SUCCESS")
                }) { throwable -> // Throw error
                    throwable.printStackTrace()
                    loaderVisible.postValue(false)

                    networkState.postValue(NetworkState.ERROR)

                    Log.e(TAG, "getAlbums: ERROR")
                })
    }

    private fun buildAlbumList(trackList: List<Track>): List<Album> {
        Log.v(TAG, "buildAlbumList")

        val albumList = mutableListOf<Album>()

        for (track in trackList) {
            albumList.find { it.id == track.albumId }?.trackList?.add(track) ?: run {
                albumList.add(Album(track.albumId, mutableListOf(track)))
            }
        }

        return albumList
    }
}