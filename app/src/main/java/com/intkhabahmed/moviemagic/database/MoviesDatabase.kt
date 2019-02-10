package com.intkhabahmed.moviemagic.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.intkhabahmed.moviemagic.models.Movie

@Database(entities = [Movie::class], version = 1, exportSchema = false)
abstract class MoviesDatabase : RoomDatabase() {

    abstract fun moviesDao(): MoviesDao

    companion object {
        private const val DATABASE_NAME = "movies.db"

        fun getInstance(context: Context): MoviesDatabase? {
            return Room.databaseBuilder(context, MoviesDatabase::class.java, DATABASE_NAME)
                .build()
        }
    }
}
