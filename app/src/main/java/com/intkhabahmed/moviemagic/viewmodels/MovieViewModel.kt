package com.intkhabahmed.moviemagic.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.intkhabahmed.moviemagic.datasource.MovieDataSourceFactory
import com.intkhabahmed.moviemagic.models.Movie

class MovieViewModel(apikey: String, keyword: String) : ViewModel() {
    private val movieDataSourceFactory = MovieDataSourceFactory(apikey, keyword)
    val moviesPagedList: LiveData<PagedList<Movie>> = LivePagedListBuilder(
        movieDataSourceFactory, PagedList.Config.Builder()
            .setPageSize(10)
            .build()
    ).build()
}