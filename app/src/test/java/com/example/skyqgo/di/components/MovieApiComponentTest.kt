package com.example.skyqgo.di.components

import com.example.skyqgo.MoviesViewModelTest
import com.example.skyqgo.basicMockkSamples.BaseTest
import com.example.skyqgo.basicMockkSamples.MovieServiceInjectTest
import com.example.skyqgo.di.component.MovieApiComponent
import com.example.skyqgo.di.modules.MovieApiModule
import com.example.skyqgo.viewmodel.MoviesViewModel
import dagger.Component
import javax.inject.Singleton



@Singleton
@Component(modules = [(MovieApiModule::class)])

interface MovieApiComponentTest : MovieApiComponent {
    fun into(moviesViewModelTest: MoviesViewModelTest)
    fun into(moviesServiceTest: MovieServiceInjectTest)
    fun into(baseTest: BaseTest)
}
