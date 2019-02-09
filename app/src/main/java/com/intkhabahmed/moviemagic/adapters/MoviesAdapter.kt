package com.intkhabahmed.moviemagic.adapters

import android.arch.paging.PagedListAdapter
import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.intkhabahmed.moviemagic.R
import com.intkhabahmed.moviemagic.databinding.MovieItemBinding
import com.intkhabahmed.moviemagic.databinding.MovieItemGridBinding
import com.intkhabahmed.moviemagic.models.Movie
import com.intkhabahmed.moviemagic.utils.Global

class MoviesAdapter : PagedListAdapter<Movie, RecyclerView.ViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            0 -> {
                val movieItemBinding: MovieItemBinding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.movie_item, parent, false)
                MovieViewHolder(movieItemBinding)
            }
            else -> {
                val movieItemBinding: MovieItemGridBinding =
                    DataBindingUtil.inflate(
                        LayoutInflater.from(parent.context),
                        R.layout.movie_item_grid,
                        parent,
                        false
                    )
                MovieGridViewHolder(movieItemBinding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, viewType: Int) {
        when (holder.itemViewType) {
            0 -> {
                (holder as MovieViewHolder).binding.movie = getItem(holder.adapterPosition)
                holder.binding.invalidateAll()
            }
            else -> {
                (holder as MovieGridViewHolder).binding.movie = getItem(holder.adapterPosition)
                holder.binding.invalidateAll()
            }
        }


    }

    class MovieViewHolder(var binding: MovieItemBinding) : RecyclerView.ViewHolder(binding.root)

    class MovieGridViewHolder(var binding: MovieItemGridBinding) : RecyclerView.ViewHolder(binding.root)

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

    override fun getItemViewType(position: Int): Int {
        return when (Global.getGridOn()) {
            false -> 0
            true -> 1
        }
    }
}