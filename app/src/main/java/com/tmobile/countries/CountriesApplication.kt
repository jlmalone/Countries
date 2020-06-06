package com.tmobile.countries

import android.app.Application
import android.content.Context

class CountriesApplication : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: CountriesApplication? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }
}