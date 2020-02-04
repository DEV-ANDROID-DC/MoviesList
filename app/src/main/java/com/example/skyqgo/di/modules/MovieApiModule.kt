package com.example.skyqgo.di.modules

import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import com.example.skyqgo.MoviesApplication
import com.example.skyqgo.util.hasNetwork

import com.example.skyqgo.data.MoviesApi
import com.example.skyqgo.data.MoviesService
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton


@Module
open class MovieApiModule(private val application: MoviesApplication)  {

    private val BASE_URL = "https://Movies-sample.herokuapp.com/"
    val cacheSize = (5 * 1024 * 1024).toLong()

    @Provides
    @Singleton
    fun giveContext(): Context = this.application

    @Provides
    @Singleton
    fun givePackageManager(): PackageManager = application.packageManager


    val myCache = Cache(giveContext().cacheDir, cacheSize)
    @Provides
    fun provideCache(context: Context): Cache {
        return Cache(context.cacheDir, cacheSize)
    }


    @Provides
    @Singleton
    fun provideHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    @Singleton
    fun provideOkHttpCache(application: Application): Cache {
        val cacheSize = 10 * 1024 * 1024 // 10 MiB
        return Cache(application.cacheDir, cacheSize.toLong())
    }

    @Provides
    open fun provideMoviesApi() : MoviesApi {
        val okHttpClient = OkHttpClient.Builder()
            .cache(myCache)
            .addInterceptor { chain ->
                var request = chain.request()
                request = if (hasNetwork(giveContext())!!)
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                else
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 10 * 60 * 1000).build()
                chain.proceed(request)
            }
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
            .create(MoviesApi::class.java)

    }

    @Provides
    @Named("test")
    open fun giveMovieApi(): MoviesApi =
        Retrofit.Builder().build().create(MoviesApi::class.java)

    @Provides
    open fun provideMoviesService() : MoviesService {
        return MoviesService()
    }

    
}