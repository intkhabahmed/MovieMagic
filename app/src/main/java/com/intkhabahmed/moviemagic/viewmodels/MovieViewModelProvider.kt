package com.intkhabahmed.moviemagic.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class MovieViewModelProvider(val apikey: String, val keyword: String, val page: Int = 1) : ViewModelProvider
.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(apikey, keyword, page) as T
    }
}