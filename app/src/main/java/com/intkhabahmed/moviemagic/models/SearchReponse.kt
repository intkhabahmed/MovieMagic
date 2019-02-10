package com.intkhabahmed.moviemagic.models

import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName
import com.intkhabahmed.moviemagic.R
import com.intkhabahmed.moviemagic.network.ApiService
import com.intkhabahmed.moviemagic.utils.Global
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

data class Movie(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    val imdbID: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Poster")
    val poster: String,
    var rating: Double,
    var plot: String
) {
    companion object {
        @BindingAdapter("android:imageUrl")
        @JvmStatic
        fun loadImage(imageView: ImageView, imageUrl: String) {
            Glide.with(imageView.context)
                .load(imageUrl)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.placeholder_movieimage)
                        .error(R.drawable.error_placeholder)
                )
                .into(imageView)
        }

        @BindingAdapter("android:rating")
        @JvmStatic
        fun setRating(view: TextView, imdbID: String) {
            ApiService.create().searchMOvieById(Global.instance!!.getString(R.string.api_key), imdbID)
                .enqueue(object : Callback<MovieDetail> {
                    override fun onFailure(call: Call<MovieDetail>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                        view.text = String.format("%s/10", response.body()?.imdbRating.toString())
                    }
                })
        }

        @BindingAdapter("android:plot")
        @JvmStatic
        fun setPlot(view: TextView, imdbID: String) {
            ApiService.create().searchMOvieById(Global.instance!!.getString(R.string.api_key), imdbID)
                .enqueue(object : Callback<MovieDetail> {
                    override fun onFailure(call: Call<MovieDetail>, t: Throwable) {

                    }

                    override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {
                        view.text = response.body()?.plot ?: "Loading..."
                    }
                })
        }
    }
}

data class Result(
    @SerializedName("Search")
    val search: MutableList<Movie?>,
    val totalResults: Int,
    @SerializedName("Response")
    val response: Boolean
)

data class MovieDetail(
    @SerializedName("Plot")
    val plot: String,
    val imdbID: String,
    val imdbRating: Double
)