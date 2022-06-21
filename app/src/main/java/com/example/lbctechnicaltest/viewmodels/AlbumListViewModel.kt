package com.example.lbctechnicaltest.viewmodels

import android.util.Log
import androidx.lifecycle.*
import androidx.savedstate.SavedStateRegistryOwner
import com.example.lbctechnicaltest.api.*
import com.example.lbctechnicaltest.database.AppDatabase
import com.example.lbctechnicaltest.models.*
import com.example.lbctechnicaltest.models.utils.NetworkState
import com.example.lbctechnicaltest.utils.*
import kotlinx.coroutines.launch

class AlbumListViewModel(
    database: AppDatabase,
    trackService: TrackService,
    schedulerProvider: BaseSchedulerProvider
) : BaseViewModel() {
    companion object {
        const val TAG = "AlbumListViewModel"
    }

    val albums = MutableLiveData<List<Album>>()
    private val trackRepository = TrackRepository(database, trackService, schedulerProvider)

    fun getAlbums() {
        Log.v(TAG, "getAlbums")

        loaderVisible.value = true
        networkState.postValue(NetworkState.PENDING)

        viewModelScope.launch {
            try {
                disposableList.add(trackRepository.getAllTracks(disposableList).subscribe {
                    albums.postValue(buildAlbumList(it))

                    loaderVisible.postValue(false)
                    networkState.postValue(NetworkState.SUCCESS)
                })
            } catch (e: LBCException) {
                networkState.postValue(NetworkState.ERROR)
            }
        }
    }

    private fun buildAlbumList(trackList: List<Track>): List<Album> {
        Log.v(TrackRepository.TAG, "buildAlbumList")

        val albumList = mutableListOf<Album>()

        for (track in trackList) {
            albumList.find { it.id == track.albumId }?.trackList?.add(track) ?: run {
                albumList.add(Album(track.albumId, mutableListOf(track)))
            }
        }

        return albumList
    }

    class AlbumListViewModelFactory(
        owner: SavedStateRegistryOwner,
        private val database: AppDatabase,
        private val trackService: TrackService = RetrofitAPIClient.trackService,
        private val schedulerProvider: BaseSchedulerProvider = SchedulerProvider()
    ) : AbstractSavedStateViewModelFactory(owner, null) {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(
            key: String,
            modelClass: Class<T>,
            state: SavedStateHandle
        ) = AlbumListViewModel(database, trackService, schedulerProvider) as T
    }
}