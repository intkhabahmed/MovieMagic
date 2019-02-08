package com.intkhabahmed.moviemagic.viewmodels

import RepositoryProvider
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.intkhabahmed.moviemagic.models.Result

class MovieViewModel(private val apikey: String, private val keyword: String, private val page: Int) : ViewModel() {
    private var movies: LiveData<Result>? = null

    fun getMovieResult(): LiveData<Result>? {
        if (movies == null) {
            Log.v(MovieViewModel::class.java.simpleName, "Loaded again from network")
            movies = RepositoryProvider.provideRepository().searchMovies(apikey, keyword, page)
        }
        return movies
    }
}