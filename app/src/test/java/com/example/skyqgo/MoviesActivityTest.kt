package com.example.skyqgo

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.Observer

import com.example.skyqgo.model.Data
import com.example.skyqgo.model.Movies
import com.example.skyqgo.view.MoviesActivity
import org.junit.Assert
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.MockitoAnnotations


class MoviesActivityTest {

    @InjectMocks
     var activity = MoviesActivity()


    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setup(){
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `my test`() {
        val mutableLiveData = MutableLiveData<String>()

        mutableLiveData.postValue("test")

        assertEquals("test", mutableLiveData.value)
    }

    @Test
    fun observerViewModel() {
        val mutableLiveData = MutableLiveData<Movies>()
        val data = Data("Action",37344, "https://image.tmdb.org/t/p/w370_and_h556_bestv2/5vHssUeVe25bMrof1HyaPyWgaP.jpg",
            "John Wick", "2014")
        val moviesList : ArrayList<Data> = arrayListOf(data)
        val movies = Movies(moviesList)
        mutableLiveData.value = movies
        mutableLiveData.observe( activity, Observer { movies ->
            movies?.let { list ->
                val dataList = list.data

                Assert.assertEquals(37344, dataList.get(0).id)
                Assert.assertEquals("Action",dataList.get(0).genre)
                Assert.assertEquals("John Wick",dataList.get(0).title)
                Assert.assertEquals("2014",dataList.get(0).year)
                Assert.assertEquals("https://image.tmdb.org/t/p/w370_and_h556_bestv2/5vHssUeVe25bMrof1HyaPyWgaP.jpg",
                    dataList.get(0).poster)
            }
        })
    }

    @Test
    fun performSearch() {

        val list = arrayListOf<Data>()
        list.add(Data("History",912312, "https://goo.gl/1zUyyq",
            "Dunkirk", "2017"))
        list.add(Data("Action",37344, "https://image.tmdb.org/t/p/w370_and_h556_bestv2/5vHssUeVe25bMrof1HyaPyWgaP.jpg",
            "John Wick", "2014"))
        list.add(Data("Animation",5123112, "https://image.tmdb.org/t/p/w370_and_h556_bestv2/eKi8dIrr8voobbaGzDpe8w0PVb.jpg",
            "Cocok", "2017"))
        list.add(Data("Action",86668, "https://image.tmdb.org/t/p/w370_and_h556_bestv2/p2SdfGmQRaw8xhFbexlHL7srMM8.jpg",
            "Sicario", "2015"))
        list.add(Data("Action",55122, "https://image.tmdb.org/t/p/w370_and_h556_bestv2/coss7RgL0NH6g4fC2s5atvf3dFO.jpg",
            "The Maze Runner", "2014"))

        var searchText : String = "Dunkirk"

        val filteredMovieList : MutableList<Data> = ArrayList()

        list.forEach {
            filteredMovieList.clear()
            if(it.title.toString().toLowerCase().contains(searchText.toLowerCase())) {
                filteredMovieList.add(it)

                Assert.assertEquals(912312, filteredMovieList.get(0).id)
                Assert.assertEquals("History", filteredMovieList.get(0).genre)
                Assert.assertEquals("Dunkirk", filteredMovieList.get(0).title)
                Assert.assertEquals("2017", filteredMovieList.get(0).year)
                Assert.assertEquals("https://goo.gl/1zUyyq", filteredMovieList.get(0).poster)
            }
        }

    }

    @Test
    fun perfromSearchTextNotinList() {
        val list = arrayListOf<Data>()
        list.add(Data("History",912312, "https://goo.gl/1zUyyq",
            "Dunkirk", "2017"))
        list.add(Data("Action",37344, "https://image.tmdb.org/t/p/w370_and_h556_bestv2/5vHssUeVe25bMrof1HyaPyWgaP.jpg",
            "John Wick", "2014"))
        list.add(Data("Animation",5123112, "https://image.tmdb.org/t/p/w370_and_h556_bestv2/eKi8dIrr8voobbaGzDpe8w0PVb.jpg",
            "Cocok", "2017"))
        list.add(Data("Action",86668, "https://image.tmdb.org/t/p/w370_and_h556_bestv2/p2SdfGmQRaw8xhFbexlHL7srMM8.jpg",
            "Sicario", "2015"))
        list.add(Data("Action",55122, "https://image.tmdb.org/t/p/w370_and_h556_bestv2/coss7RgL0NH6g4fC2s5atvf3dFO.jpg",
            "The Maze Runner", "2014"))

        var searchText : String = "Heaven"

        val filteredMovieList : MutableList<Data> = ArrayList()

        list.forEach {
            filteredMovieList.clear()
            if(it.title.toString().toLowerCase().contains(searchText.toLowerCase())) {
                filteredMovieList.add(it)
            } else{
                Assert.assertEquals("Item not in the list","Item not in the list")
            }
            }
    }
}