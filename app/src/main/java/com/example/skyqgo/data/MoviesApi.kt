package com.example.skyqgo.data

import com.example.skyqgo.model.Movies
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Url

interface MoviesApi {

    @GET("api/Movies")
    fun getMovies() : Single<Movies>

    //for testing
    @GET
    fun getMoviesTempWithUrl(
        @Url
        url: String
    ): Flowable<Movies>
}