package com.intkhabahmed.moviemagic.network

import com.intkhabahmed.moviemagic.models.Result
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    @GET("/")
    fun searchMovies(
        @Query("apikey") apiKey: String,
        @Query("s") term: String,
        @Query("page") page: Int
    ): Call<Result>

    companion object Factory {
        private const val BASE_URL: String = "http://www.omdbapi.com"
        fun create(): ApiService {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}
