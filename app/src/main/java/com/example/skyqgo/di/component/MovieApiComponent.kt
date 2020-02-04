package com.example.skyqgo.di.component

import com.example.skyqgo.MoviesApplication

import com.example.skyqgo.data.MoviesService
import com.example.skyqgo.di.modules.MovieApiModule
import com.example.skyqgo.viewmodel.MoviesViewModel
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MovieApiModule::class])
interface MovieApiComponent {
    fun inject(into: MoviesApplication)
    fun inject(into: MoviesService)
    fun inject(into: MoviesViewModel)
}