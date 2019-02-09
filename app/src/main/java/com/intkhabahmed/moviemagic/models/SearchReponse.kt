package com.intkhabahmed.moviemagic.models

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName
import com.intkhabahmed.moviemagic.R

data class Movie(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    val imdbID: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Poster")
    val poster: String
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
    }
}

data class Result(
    @SerializedName("Search")
    val search: MutableList<Movie?>,
    val totalResults: Int,
    @SerializedName("Response")
    val response: Boolean
)