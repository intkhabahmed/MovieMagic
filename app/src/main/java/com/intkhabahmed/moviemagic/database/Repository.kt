package com.intkhabahmed.moviemagic.database

import android.arch.lifecycle.Observer
import com.intkhabahmed.moviemagic.models.MovieDetail
import com.intkhabahmed.moviemagic.models.Result
import com.intkhabahmed.moviemagic.network.ApiService
import com.intkhabahmed.moviemagic.utils.AppExecutors
import com.intkhabahmed.moviemagic.utils.Global
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {
    fun loadAndInsertMoviesToDB(apikey: String, keyword: String) {
        val movieCount = Global.dbInstance().moviesDao().getMoviesCount()
        val observer = Observer<Int> {
            if (it!! == 0) {
                ApiService.create().searchMovies(apikey, keyword, 1).enqueue(object : Callback<Result> {
                    override fun onFailure(call: Call<Result>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<Result>, response: Response<Result>) {
                        val totalPages = response.body()?.totalResults!! / 10

                        for (i in 1..totalPages) {
                            ApiService.create().searchMovies(apikey, keyword, i).enqueue(object : Callback<Result> {
                                override fun onFailure(call: Call<Result>, t: Throwable) {

                                }

                                override fun onResponse(call: Call<Result>, response: Response<Result>) {
                                    response.body()?.search?.map { it1 ->
                                        if (it1 == null) return
                                        ApiService.create().searchMovieById(apikey, it1.imdbID)
                                            .enqueue(object : Callback<MovieDetail> {
                                                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {

                                                }

                                                override fun onResponse(
                                                    call: Call<MovieDetail>,
                                                    response: Response<MovieDetail>
                                                ) {
                                                    val detail = response.body()
                                                    it1.plot = detail?.plot ?: ""
                                                    it1.rating = detail?.imdbRating ?: 0.0
                                                    AppExecutors.diskIO.execute {
                                                        Global.dbInstance().moviesDao().insertMovie(it1)
                                                    }
                                                }
                                            })
                                    }
                                }
                            })
                        }
                    }
                })
            }
        }
        movieCount.observeForever(observer)
    }

}