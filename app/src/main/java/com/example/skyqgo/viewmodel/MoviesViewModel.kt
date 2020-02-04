package com.example.skyqgo.viewmodel

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.util.Log
import com.example.skyqgo.MoviesApplication

import com.example.skyqgo.data.MoviesService

import com.example.skyqgo.model.Movies
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

open class MoviesViewModel() : ViewModel() {

    @Inject lateinit var moviesService: MoviesService
      //var application: Application = Application()
    private val disposable = CompositeDisposable()

    val movies = MutableLiveData<Movies>()
    val movieLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()


    init {
    // try catch block was added for testing
    try {

        MoviesApplication.INSTANCE.appComponent.inject(this)
        //DaggerMovieApiComponent.create().inject(this)
    } catch (e:Exception){
        e.printStackTrace()
    }
    }

    fun refresh() {
        fetchMovies()
    }

    private fun fetchMovies() {
        loading.value = true
        disposable.add(moviesService.getMovies().subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<Movies> () {
                override fun onSuccess(value: Movies) {
                    movies.value = value
                    movieLoadError.value = false
                    loading.value = false
                }

                override fun onError(e: Throwable) {
                    Log.e("TAG"," Error Occured :: "+e)
                    movieLoadError.value = true
                    loading.value = false
                }

            }))
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}