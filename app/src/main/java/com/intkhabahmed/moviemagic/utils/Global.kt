package com.intkhabahmed.moviemagic.utils

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager

class Global : Application() {


    override fun onCreate() {
        super.onCreate()
        instance = applicationContext as Global
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(applicationContext)
    }

    companion object {
        private var instance: Global? = null
        private var sharedPreference: SharedPreferences? = null
        fun setGridOn(isGridOn: Boolean) {
            sharedPreference!!.edit().putBoolean(Constants.GRID_MODE, isGridOn).apply()
        }

        fun getGridOn(): Boolean {
            return sharedPreference!!.getBoolean(Constants.GRID_MODE, false)
        }
    }
}