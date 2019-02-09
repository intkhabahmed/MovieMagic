package com.intkhabahmed.moviemagic.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.intkhabahmed.moviemagic.R
import com.intkhabahmed.moviemagic.adapters.MoviesAdapter
import com.intkhabahmed.moviemagic.databinding.FragmentHomeBinding
import com.intkhabahmed.moviemagic.viewmodels.MovieViewModel
import com.intkhabahmed.moviemagic.viewmodels.MovieViewModelProvider

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val recyclerView = homeBinding.moviesRv
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()

        val moviesAdapter = MoviesAdapter()
        recyclerView.adapter = moviesAdapter

        val factory = MovieViewModelProvider(getString(R.string.api_key), "Lord")
        val movieViewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)
        movieViewModel.moviesPagedList.observe(viewLifecycleOwner, Observer {
            homeBinding.loadingPb.visibility = View.GONE
            it?.sortBy { it.year }
            moviesAdapter.submitList(it)
        })
    }
}