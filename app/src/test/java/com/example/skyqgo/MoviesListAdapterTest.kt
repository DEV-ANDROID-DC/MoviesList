package com.example.skyqgo

import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.skyqgo.model.Data
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.MockitoAnnotations

class MoviesListAdapterTest {


    @get:Rule
    var rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
    }

    @Test
    fun `my test`() {
        val list = arrayListOf<Data>()

        list.add(Data("History",912312, "https://goo.gl/1zUyyq",
            "Dunkirk", "2017"))

        Assert.assertEquals("History", list.get(0).genre)
    }

    @Test
    fun getListItemCount() {
        val list = arrayListOf<Data>()

        list.add(Data("History",912312, "https://goo.gl/1zUyyq",
            "Dunkirk", "2017"))

        Assert.assertEquals(1,list.size)
    }
}