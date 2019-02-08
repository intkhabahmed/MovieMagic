package com.intkhabahmed.moviemagic.models

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: Int,
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
                .into(imageView)
        }
    }
}

data class Result(
    @SerializedName("Search")
    val search: List<Movie>,
    val totalResults: Int,
    @SerializedName("Response")
    val response: Boolean
)