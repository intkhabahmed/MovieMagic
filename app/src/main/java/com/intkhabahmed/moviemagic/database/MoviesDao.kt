package com.intkhabahmed.moviemagic.database

import android.arch.lifecycle.LiveData
import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.intkhabahmed.moviemagic.models.Movie

@Dao
interface MoviesDao {
    @Query("SELECT * FROM movies ORDER BY CASE :param WHEN 'rating' THEN rating WHEN 'year' THEN year ELSE title END")
    fun getSortedMovies(param: String): DataSource.Factory<Int, Movie>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMovie(movie: Movie)

    @Query("SELECT COUNT(*) FROM movies")
    fun getMoviesCount(): LiveData<Int>

    @Query("DELETE FROM movies")
    fun deleteAll()
}
