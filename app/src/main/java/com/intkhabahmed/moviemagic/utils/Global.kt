package com.intkhabahmed.moviemagic.utils

import android.app.Application
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.intkhabahmed.moviemagic.database.MoviesDatabase

class Global : Application() {


    override fun onCreate() {
        super.onCreate()
        instance = applicationContext as Global
        sharedPreference = PreferenceManager.getDefaultSharedPreferences(applicationContext)
        db = MoviesDatabase.getInstance(applicationContext)
    }

    companion object {
        var instance: Global? = null
        private var sharedPreference: SharedPreferences? = null
        private var db: MoviesDatabase? = null
        fun setGridOn(isGridOn: Boolean) {
            sharedPreference!!.edit().putBoolean(Constants.GRID_MODE, isGridOn).apply()
        }

        fun getGridOn(): Boolean {
            return sharedPreference!!.getBoolean(Constants.GRID_MODE, false)
        }

        fun dbInstance(): MoviesDatabase {
            return db!!
        }

        fun setSortCriteria(sortBy: String, order: Int) {
            sharedPreference!!.edit().putString(Constants.SORT_BY, sortBy)
                .putInt(Constants.ORDER_ID, order).apply()
        }

        fun getSortCriteria(): String {
            return sharedPreference!!.getString(Constants.SORT_BY, "")!!
        }

        fun getSortId(): Int {
            return sharedPreference!!.getInt(Constants.ORDER_ID, 1)
        }
    }
}