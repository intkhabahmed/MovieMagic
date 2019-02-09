package com.intkhabahmed.moviemagic.datasource

import android.arch.paging.PageKeyedDataSource
import com.intkhabahmed.moviemagic.models.Movie
import com.intkhabahmed.moviemagic.models.Result
import com.intkhabahmed.moviemagic.network.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieDataSource(val apikey: String, val keyword: String) : PageKeyedDataSource<Int, Movie>() {
    private var totalPages = 1
    override fun loadInitial(params: LoadInitialParams<Int>, callback: LoadInitialCallback<Int, Movie>) {
        ApiService.create().searchMovies(apikey, keyword, 1).enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {

            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                totalPages = Math.ceil(response.body()!!.totalResults / 10.0).toInt()
                callback.onResult(response.body()!!.search, 0, response.body()!!.search.size, null, 2)
            }
        })
    }

    override fun loadAfter(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        ApiService.create().searchMovies(apikey, keyword, params.key).enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {

            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                callback.onResult(response.body()!!.search, if (params.key < totalPages) params.key + 1 else null)
            }
        })
    }

    override fun loadBefore(params: LoadParams<Int>, callback: LoadCallback<Int, Movie>) {
        ApiService.create().searchMovies(apikey, keyword, params.key).enqueue(object : Callback<Result> {
            override fun onFailure(call: Call<Result>, t: Throwable) {

            }

            override fun onResponse(call: Call<Result>, response: Response<Result>) {
                callback.onResult(response.body()!!.search, if (params.key > 1) params.key - 1 else null)
            }
        })
    }

}