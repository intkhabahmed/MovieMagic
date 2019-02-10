package com.intkhabahmed.moviemagic.utils

import java.util.concurrent.Executors

class AppExecutors {
    companion object {
        val diskIO = Executors.newSingleThreadExecutor()
    }
}