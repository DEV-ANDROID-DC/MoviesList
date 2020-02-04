package com.example.skyqgo

import android.support.test.espresso.Espresso
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import com.example.skyqgo.CustomAssertions.Companion.hasItemCount
import com.example.skyqgo.CustomMatchers.Companion.withItemCount
import com.example.skyqgo.view.MoviesActivity
import org.junit.Rule
import org.junit.Test

class MoviesActivityAndroidTest {

    @Rule
    @JvmField
    var activityRule = ActivityTestRule<MoviesActivity>(MoviesActivity::class.java)

    @Test
    fun countMovies() {
        Espresso.onView(ViewMatchers.withId(R.id.moviesList))
            .check(ViewAssertions.matches(withItemCount(25)))
    }

    @Test
    fun countMoviesWithViewAssertion() {
        Espresso.onView(ViewMatchers.withId(R.id.moviesList))
            .check(hasItemCount(25))
    }
}