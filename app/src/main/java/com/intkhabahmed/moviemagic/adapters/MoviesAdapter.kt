package com.intkhabahmed.moviemagic.adapters

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.intkhabahmed.moviemagic.R
import com.intkhabahmed.moviemagic.databinding.MovieItemBinding
import com.intkhabahmed.moviemagic.models.Movie
import com.intkhabahmed.moviemagic.models.Result

class MoviesAdapter : RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    private var result: Result? = null
    private lateinit var movies: List<Movie>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val adapterBinding: MovieItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.movie_item, parent, false)
        return MovieViewHolder(adapterBinding)
    }

    override fun getItemCount(): Int {
        return result?.search?.size ?: 0
    }

    override fun onBindViewHolder(holder: MovieViewHolder, viewType: Int) {
        holder.binding.movie = movies[holder.adapterPosition]
        holder.binding.invalidateAll()
    }

    class MovieViewHolder(var binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    fun setResult(result: Result) {
        this.result = result
        this.movies = result.search
        notifyDataSetChanged()
    }
}