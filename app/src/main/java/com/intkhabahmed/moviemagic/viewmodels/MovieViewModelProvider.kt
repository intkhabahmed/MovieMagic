package com.intkhabahmed.moviemagic.viewmodels

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider

@Suppress("UNCHECKED_CAST")
class MovieViewModelProvider(private val apikey: String, private val keyword: String) : ViewModelProvider
.NewInstanceFactory() {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MovieViewModel(apikey, keyword) as T
    }
}