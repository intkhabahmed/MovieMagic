package com.intkhabahmed.moviemagic.ui

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.intkhabahmed.moviemagic.R
import com.intkhabahmed.moviemagic.fragments.HomeFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.home_fragment, HomeFragment()).commit()
    }
}
