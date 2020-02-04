package com.example.skyqgo.basicMockkSamples

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito
import org.mockito.Spy
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class Spy {

    @Spy
    var spyList = mutableListOf<String>()


    @Test
    fun testSpyList() {
        spyList.add("John Wick") //  calls `add()` func of mutableList
        Mockito.verify(spyList).add("John Wick")
        Assert.assertEquals(1, spyList.size)
        Assert.assertNotNull(spyList[0])
    }

}