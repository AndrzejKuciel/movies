package com.andysworkshop.movies

import androidx.fragment.app.testing.launchFragmentInContainer
import com.andysworkshop.movies.popularmoviesscreen.PopularMoviesFragment
import com.andysworkshop.movies.popularmoviesscreen.di.PopularMoviesViewModelFactoryModule

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config


@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@UninstallModules(PopularMoviesViewModelFactoryModule::class)
class PopularMoviesFragmentUnitTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun `test if saveRecyclerPositionInViewModel called when save position flag is true onDestroyView()`() {
        val scenario = launchFragmentInHiltContainer<PopularMoviesFragment>()
        //launchFragmentInContainer {  }

    }
}

