package com.example.lbctechnicaltest.api

import androidx.annotation.WorkerThread
import com.example.lbctechnicaltest.database.TrackDao
import com.example.lbctechnicaltest.models.Track

class AlbumRepository(private val trackDao: TrackDao) {
    val allTracks: List<Track> = trackDao.getAll()

    @WorkerThread
    suspend fun insert(track: Track) {
        trackDao.insert(track)
    }
}