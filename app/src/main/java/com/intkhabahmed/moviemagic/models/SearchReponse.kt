package com.intkhabahmed.moviemagic.models

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.databinding.BindingAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.annotations.SerializedName
import com.intkhabahmed.moviemagic.R

@Entity(tableName = "movies")
data class Movie(
    @SerializedName("Title")
    val title: String,
    @SerializedName("Year")
    val year: String,
    @PrimaryKey(autoGenerate = false)
    val imdbID: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Poster")
    val poster: String?,
    var rating: Double?,
    var plot: String?
) {
    companion object {
        @BindingAdapter("android:imageUrl")
        @JvmStatic
        fun loadImage(imageView: ImageView, imageUrl: String?) {
            Glide.with(imageView.context)
                .load(imageUrl?.trim())
                .thumbnail(0.5F)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.placeholder_movieimage)
                        .error(R.drawable.error_placeholder)
                )
                .into(imageView)
        }

        @BindingAdapter("android:rating")
        @JvmStatic
        fun setRating(view: TextView, imdbRating: Double) {
            view.text = String.format("%s/10", imdbRating)
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