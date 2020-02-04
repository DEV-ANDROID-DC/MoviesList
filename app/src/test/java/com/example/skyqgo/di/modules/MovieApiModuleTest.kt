package com.example.skyqgo.di.modules

import android.app.Application
import com.example.skyqgo.MoviesApplication
import com.example.skyqgo.data.MoviesApi
import com.example.skyqgo.data.MoviesService
import io.mockk.mockk
import okhttp3.OkHttpClient
import org.mockito.Mockito.mock

class MovieApiModuleTest(application: MoviesApplication) : MovieApiModule(application) {

   override fun giveMovieApi() : MoviesApi = mockk()

    override fun provideMoviesApi(): MoviesApi = mockk()

    override fun provideMoviesService() : MoviesService = mockk()
}