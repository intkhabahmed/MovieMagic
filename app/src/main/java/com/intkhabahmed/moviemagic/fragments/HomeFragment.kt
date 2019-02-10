package com.intkhabahmed.moviemagic.fragments

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.arch.paging.PagedList
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import com.intkhabahmed.moviemagic.R
import com.intkhabahmed.moviemagic.adapters.MoviesAdapter
import com.intkhabahmed.moviemagic.database.Repository
import com.intkhabahmed.moviemagic.databinding.FragmentHomeBinding
import com.intkhabahmed.moviemagic.models.Movie
import com.intkhabahmed.moviemagic.utils.Constants
import com.intkhabahmed.moviemagic.utils.Global
import com.intkhabahmed.moviemagic.viewmodels.MovieViewModel

class HomeFragment : Fragment() {

    private lateinit var homeBinding: FragmentHomeBinding
    private lateinit var recyclerView: RecyclerView
    private lateinit var moviesAdapter: MoviesAdapter
    private var isGridLayout = false
    private var observer: Observer<PagedList<Movie>>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Repository.loadAndInsertMoviesToDB(getString(R.string.api_key), "Lord")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        isGridLayout = Global.getGridOn()

        homeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        return homeBinding.root
    }

    private var noOfGridColumns: Int = 2

    private lateinit var movieViewModel: MovieViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recyclerView = homeBinding.moviesRv
        noOfGridColumns = resources.displayMetrics.widthPixels / resources.getDimension(R.dimen.poster_width).toInt()
        recyclerView.layoutManager =
            if (Global.getGridOn()) {
                GridLayoutManager(activity, noOfGridColumns)
            } else {
                GridLayoutManager(activity, 1)
            }
        recyclerView.setHasFixedSize(true)
        recyclerView.itemAnimator = DefaultItemAnimator()

        moviesAdapter = MoviesAdapter()
        recyclerView.adapter = moviesAdapter

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        movieViewModel.makeLivePagedList(Global.dbInstance().moviesDao().getSortedMovies(Global.getSortCriteria()))
        observer = Observer {
            moviesAdapter.submitList(it)
            if (it.isNullOrEmpty()) {
                homeBinding.loadingPb.visibility = View.VISIBLE
            } else {
                homeBinding.loadingPb.visibility = View.GONE
            }
        }
        movieViewModel.moviesPagedList!!.observe(viewLifecycleOwner, observer!!)
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        inflater?.inflate(R.menu.main_menu, menu)
        menu?.findItem(R.id.layout_type)?.icon =
            if (isGridLayout) {
                context?.let { ContextCompat.getDrawable(it, R.drawable.ic_grid_off_24dp) }
            } else context?.let { ContextCompat.getDrawable(it, R.drawable.ic_grid_on_24dp) }
        menu!!.getItem(1).subMenu.getItem(Global.getSortId() - 1).isChecked = true
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
                    } else GridLayoutManager(activity, 1)
                return true
            }
            R.id.default_sort -> {
                setAndHandleSortCriteria(item, Constants.DEFAULT)
            }
            R.id.sort_by_release -> {
                setAndHandleSortCriteria(item, Constants.YEAR)
                return true
            }
            R.id.sort_by_rating -> {
                setAndHandleSortCriteria(item, Constants.RATING)
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    private fun setAndHandleSortCriteria(item: MenuItem, criteria: String) {
        item.isChecked = !item.isChecked
        Global.setSortCriteria(criteria, item.order)
        handleObserver()
    }

    fun handleObserver() {
        movieViewModel.makeLivePagedList(Global.dbInstance().moviesDao().getSortedMovies(Global.getSortCriteria()))
        movieViewModel.moviesPagedList!!.removeObserver(observer!!)
        movieViewModel.moviesPagedList!!.observe(viewLifecycleOwner, observer!!)
    }
}