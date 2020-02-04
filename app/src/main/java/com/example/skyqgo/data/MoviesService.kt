package com.example.skyqgo.data

import com.example.skyqgo.MoviesApplication

import com.example.skyqgo.model.Movies
import io.reactivex.Single
import javax.inject.Inject

class MoviesService()   {

    @Inject lateinit var api: MoviesApi

    init {
        MoviesApplication.INSTANCE.appComponent.inject(this)
    }

    fun getMovies(): Single<Movies> {
        return api.getMovies()
    }
}