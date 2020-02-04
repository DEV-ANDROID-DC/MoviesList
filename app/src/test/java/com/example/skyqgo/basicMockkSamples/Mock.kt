package com.example.skyqgo.basicMockkSamples

import junit.framework.TestCase
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class Mock {

    @Mock
    lateinit var mockList: MutableList<String>


    @Test
    fun testMockList() {
        mockList.add("Dunkirk")    //  doesn't call `add()` func of mutableList
        Mockito.verify(mockList).add("Dunkirk")
        Assert.assertEquals(0, mockList.size)
        TestCase.assertNull(mockList[0])
    }
}