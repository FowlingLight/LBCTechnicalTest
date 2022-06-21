package com.example.lbctechnicaltest.database

import androidx.room.*
import com.example.lbctechnicaltest.models.Track

@Dao
interface TrackDao {
    @Query("SELECT * FROM track")
    fun getAll(): List<Track>

    @Insert
    fun insertAll(tracks: List<Track>)

    @Insert
    fun insert(track: Track)

    @Query("DELETE FROM track")
    fun deleteAll()
}