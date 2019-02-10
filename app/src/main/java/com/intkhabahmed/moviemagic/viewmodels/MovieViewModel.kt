package com.intkhabahmed.moviemagic.viewmodels

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.arch.paging.DataSource
import android.arch.paging.LivePagedListBuilder
import android.arch.paging.PagedList
import com.intkhabahmed.moviemagic.models.Movie

class MovieViewModel() : ViewModel() {
    var moviesPagedList: LiveData<PagedList<Movie>>? = null
    fun makeLivePagedList(dataSource: DataSource.Factory<Int, Movie>) {
        moviesPagedList = null
        moviesPagedList = LivePagedListBuilder(
            dataSource, PagedList.Config.Builder()
                .setPageSize(10)
                .setInitialLoadSizeHint(10)
                .build()
        ).build()
    }
}