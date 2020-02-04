package com.example.skyqgo.basicMockkSamples

import com.example.skyqgo.MoviesApplication
import com.example.skyqgo.data.MoviesApi
import com.example.skyqgo.di.component.DaggerMovieApiComponent
import com.example.skyqgo.di.components.DaggerMovieApiComponentTest
import com.example.skyqgo.di.modules.MovieApiModuleTest
import com.example.skyqgo.model.Data
import com.example.skyqgo.model.Movies
import io.mockk.every
import io.reactivex.Flowable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import javax.inject.Inject

class MovieServiceInjectTest {

    @Inject lateinit var moviesApi : MoviesApi

    @Before
    fun setUp() {
        val component = DaggerMovieApiComponentTest.builder()
            .movieApiModule(MovieApiModuleTest(MoviesApplication()))
            .build()
        component.into(this)
    }

    @Test
    fun `my test`() {
        val data = Data("Action",37344, "https://image.tmdb.org/t/p/w370_and_h556_bestv2/5vHssUeVe25bMrof1HyaPyWgaP.jpg",
            "John Wick", "2014")
        val moviesList : ArrayList<Data> = arrayListOf(data)
        Assert.assertNotNull(moviesApi)
        every { moviesApi.getMoviesTempWithUrl("r")  } returns Flowable.just(Movies(moviesList))
        val result = moviesApi.getMoviesTempWithUrl("r")
        result.test().assertValue(Movies(moviesList))
    }
}