package com.intkhabahmed.moviemagic.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.intkhabahmed.moviemagic.R
import com.intkhabahmed.moviemagic.adapters.MoviesAdapter
import com.intkhabahmed.moviemagic.databinding.FragmentHomeBinding
import com.intkhabahmed.moviemagic.utils.Global
import com.intkhabahmed.moviemagic.viewmodels.MovieViewModel
import com.intkhabahmed.moviemagic.viewmodels.MovieViewModelProvider

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private var isGridLayout = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        isGridLayout = Global.getGridOn()
        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return homeBinding.root
    }

    private var noOfGridColumns: Int = 2

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = homeBinding.moviesRv
        noOfGridColumns = resources.displayMetrics.widthPixels / resources.getDimension(R.dimen.poster_width).toInt()
        recyclerView.layoutManager =
            if (Global.getGridOn()) {
                GridLayoutManager(activity, noOfGridColumns)
            } else {
                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
            }
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()

        moviesAdapter = MoviesAdapter()
        recyclerView.adapter = moviesAdapter

        val factory = MovieViewModelProvider(getString(R.string.api_key), "Lord")
        val movieViewModel = ViewModelProviders.of(this, factory).get(MovieViewModel::class.java)
        movieViewModel.moviesPagedList.observe(viewLifecycleOwner, Observer {
            homeBinding.loadingPb.visibility = View.GONE
            moviesAdapter.submitList(it)
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)
        menu?.findItem(R.id.layout_type)?.icon =
            if (isGridLayout) {
                context?.let { ContextCompat.getDrawable(it, R.drawable.ic_grid_off_24dp) }
            } else context?.let { ContextCompat.getDrawable(it, R.drawable.ic_grid_on_24dp) }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        val id = item?.itemId
        when (id) {
            R.id.layout_type -> {
                isGridLayout = !isGridLayout
                Global.setGridOn(isGridLayout)
                item.icon =
                    if (isGridLayout) {
                        context?.let { ContextCompat.getDrawable(it, R.drawable.ic_grid_off_24dp) }
                    } else context?.let { ContextCompat.getDrawable(it, R.drawable.ic_grid_on_24dp) }
                recyclerView.layoutManager =
                    if (isGridLayout) {
                        GridLayoutManager(activity, noOfGridColumns)
                    } else LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                recyclerView.adapter = moviesAdapter
                return true
            }
            R.id.sort_by_release -> {
            }
            R.id.sort_by_rating -> {
            }
        }

        return super.onOptionsItemSelected(item)
    }
}