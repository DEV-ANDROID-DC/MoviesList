package com.example.skyqgo

import android.app.Application
import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.util.Log
import com.example.skyqgo.data.MoviesService
import com.example.skyqgo.di.modules.MovieApiModuleTest
import com.example.skyqgo.model.Data
import com.example.skyqgo.model.Movies
import com.example.skyqgo.viewmodel.MoviesViewModel
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.disposables.Disposable
import io.reactivex.internal.schedulers.ExecutorScheduler
import io.reactivex.plugins.RxJavaPlugins
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import java.util.concurrent.Callable
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

class MoviesViewModelTest() {


    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Mock
    lateinit var moviesService: MoviesService

    @InjectMocks
    var moviesViewModel = MoviesViewModel()

    private var testSingle: Single<Movies>? = null


    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun getMoviesSuccess() {

        val data = Data("History",912312, "https://goo.gl/1zUyyq",
             "Dunkirk", "2017")
        val moviesList : ArrayList<Data> = arrayListOf(data)
        val movies = Movies(moviesList)
        Log.e("Movie ", "is :: "+movies)
        //testSingle = Single.just(movies)
        testSingle = Single.just(Movies(moviesList))
        `when`(moviesService.getMovies()).thenReturn(testSingle)
        moviesViewModel.refresh()

        Assert.assertEquals(1,moviesViewModel.movies.value?.data?.size)
        Assert.assertEquals(false,moviesViewModel.movieLoadError.value)
        Assert.assertEquals(false,moviesViewModel.loading.value)
    }

    @Test
    fun getMovieDataItems() {

        val data = Data("Action",37344, "https://image.tmdb.org/t/p/w370_and_h556_bestv2/5vHssUeVe25bMrof1HyaPyWgaP.jpg",
            "John Wick", "2014")
        val moviesList : ArrayList<Data> = arrayListOf(data)
        val movies = Movies(moviesList)
        Log.e("Movie ", "is :: "+movies)
        //testSingle = Single.just(movies)
        testSingle = Single.just(Movies(moviesList))
        `when`(moviesService.getMovies()).thenReturn(testSingle)
        moviesViewModel.refresh()

        Assert.assertEquals(37344,moviesViewModel.movies.value?.data?.get(0)?.id)
        Assert.assertEquals("Action",moviesViewModel.movies.value?.data?.get(0)?.genre)
        Assert.assertEquals("https://image.tmdb.org/t/p/w370_and_h556_bestv2/5vHssUeVe25bMrof1HyaPyWgaP.jpg",
            moviesViewModel.movies.value?.data?.get(0)?.poster)
        Assert.assertEquals("John Wick",moviesViewModel.movies.value?.data?.get(0)?.title)
        Assert.assertEquals("2014",moviesViewModel.movies.value?.data?.get(0)?.year)

    }

    @Test
    fun getMoviesFail() {
        testSingle = Single.error(Throwable())
        `when`(moviesService.getMovies()).thenReturn(testSingle)
        moviesViewModel.refresh()
        Assert.assertEquals(true,moviesViewModel.movieLoadError.value)
        Assert.assertEquals(false,moviesViewModel.loading.value)
    }


    @Before
    fun setUpRxSchedulers(){
        val immidiate = object: Scheduler() {
            override fun scheduleDirect(run: Runnable, delay: Long, unit: TimeUnit): Disposable {
                return super.scheduleDirect(run, 0, unit)
            }

            override fun createWorker(): Worker {
                return ExecutorScheduler.ExecutorWorker(Executor { it.run() })
            }
        }

        RxJavaPlugins.setInitIoSchedulerHandler{ scheduler: Callable<Scheduler>? -> immidiate }
        RxJavaPlugins.setInitComputationSchedulerHandler { scheduler: Callable<Scheduler>? -> immidiate }
        RxJavaPlugins.setInitNewThreadSchedulerHandler { scheduler: Callable<Scheduler>? ->  immidiate}
        RxJavaPlugins.setInitSingleSchedulerHandler { scheduler: Callable<Scheduler>? -> immidiate }
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler: Callable<Scheduler>? -> immidiate }

    }
}