package com.example.lbctechnicaltest.database

import android.content.Context
import androidx.room.*
import com.example.lbctechnicaltest.models.Track

/**
 * Small Room database storing all the songs in a table
 */
@Database(entities = [Track::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun trackDao(): TrackDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "lbc_database"
                ).build()
                INSTANCE = instance

                instance
            }
        }
    }
}