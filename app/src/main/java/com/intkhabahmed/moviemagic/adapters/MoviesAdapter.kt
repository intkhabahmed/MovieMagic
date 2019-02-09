package com.intkhabahmed.moviemagic.adapters

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.intkhabahmed.moviemagic.R
import com.intkhabahmed.moviemagic.databinding.MovieItemBinding
import com.intkhabahmed.moviemagic.models.Movie

class MoviesAdapter : PagedListAdapter<Movie, MoviesAdapter.MovieViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val adapterBinding: MovieItemBinding =
            DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.movie_item, parent, false)
        return MovieViewHolder(adapterBinding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, viewType: Int) {
        holder.binding.movie = getItem(holder.adapterPosition)
        holder.binding.invalidateAll()
    }

    class MovieViewHolder(var binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(old: Movie, new: Movie): Boolean {
                return old.imdbID == new.imdbID
            }

            override fun areContentsTheSame(old: Movie, new: Movie): Boolean {
                return old == new
            }
        }
    }
}