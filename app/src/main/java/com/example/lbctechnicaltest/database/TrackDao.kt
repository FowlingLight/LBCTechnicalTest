package com.example.lbctechnicaltest.database

import androidx.room.*
import com.example.lbctechnicaltest.models.Track

/**
 * Simple DAO to store the Tracks in a database
 */
@Dao
interface TrackDao {
    @Query("SELECT * FROM track")
    fun getAll(): List<Track>

    @Insert
    fun insertAll(tracks: List<Track>)

    @Query("DELETE FROM track")
    fun deleteAll()
}