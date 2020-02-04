package com.example.skyqgo

import android.app.Application
import com.example.skyqgo.di.component.DaggerMovieApiComponent
import com.example.skyqgo.di.component.MovieApiComponent
import com.example.skyqgo.di.modules.MovieApiModule
import com.facebook.stetho.Stetho

open class MoviesApplication : Application() {
    open lateinit var appComponent: MovieApiComponent

    companion object {
         lateinit var INSTANCE: MoviesApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
        appComponent = getApplicationComponent()
        appComponent.inject(this)
        Stetho.initializeWithDefaults(this)

    }

    open fun getApplicationComponent(): MovieApiComponent =
        DaggerMovieApiComponent
            .builder()
            .movieApiModule(MovieApiModule(this))
            .build()
}