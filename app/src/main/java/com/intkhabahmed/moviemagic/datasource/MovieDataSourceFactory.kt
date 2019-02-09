package com.intkhabahmed.moviemagic.datasource

import android.arch.lifecycle.MutableLiveData
import android.arch.paging.DataSource
import android.arch.paging.PageKeyedDataSource
import com.intkhabahmed.moviemagic.models.Movie

class MovieDataSourceFactory(val apikey: String, val keyword: String) : DataSource.Factory<Int, Movie>() {
    val movieLiveDataSource: MutableLiveData<PageKeyedDataSource<Int, Movie>>
        get() = MutableLiveData()

    override fun create(): DataSource<Int, Movie> {
        val movieDataSource = MovieDataSource(apikey, keyword)
        movieLiveDataSource.postValue(movieDataSource)
        return movieDataSource
    }
}