package com.example.skyqgo.basicMockkSamples

import com.example.skyqgo.MoviesApplication
import com.example.skyqgo.data.MoviesApi
import com.example.skyqgo.di.components.DaggerMovieApiComponentTest
import com.example.skyqgo.di.modules.MovieApiModuleTest
import javax.inject.Inject

class BaseTest {

    @Inject
    lateinit var moviesApi : MoviesApi

    fun setUp() {
        val component = DaggerMovieApiComponentTest.builder()
            .movieApiModule(MovieApiModuleTest(MoviesApplication()))
            .build()
        component.into(this)
    }
}