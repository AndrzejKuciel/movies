package com.andysworkshop.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import app.cash.turbine.test
import com.andysworkshop.movies.popularmoviesscreen.PopularMoviesFragment
import com.andysworkshop.movies.popularmoviesscreen.PopularMoviesViewModel
import com.andysworkshop.movies.popularmoviesscreen.data.PopularMoviesUIData
import com.andysworkshop.movies.popularmoviesscreen.di.PopularMoviesScope
import com.andysworkshop.movies.popularmoviesscreen.di.PopularMoviesViewModelFactoryModule
import com.andysworkshop.movies.popularmoviesscreen.usecases.IObservePopularMoviesDataUseCase
import com.andysworkshop.movies.popularmoviesscreen.usecases.IRequestPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.HiltTestApplication
import dagger.hilt.android.testing.UninstallModules
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import javax.inject.Inject


@HiltAndroidTest
@Config(application = HiltTestApplication::class)
@RunWith(RobolectricTestRunner::class)
@UninstallModules(PopularMoviesViewModelFactoryModule::class)
class PopularMoviesFragmentUnitTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Test
    fun `test if saveRecyclerPositionInViewModel called when save position flag is true onDestroyView()`() {
        launchFragmentInHiltContainer<PopularMoviesFragment> {
            this.onDestroyView()
            verify { mockViewModel.saveRecyclerPosition(any()) }
        }
    }

    @Test
    fun `test list of movies ui data gets updated along with viewmodel sharedflow`() = runTest {

        lateinit var sut: PopularMoviesFragment

        val moviesSharedFlow = MutableSharedFlow<List<PopularMoviesUIData>>()
        every {mockViewModel.moviesSharedFlow} returns moviesSharedFlow

        launchFragmentInHiltContainer<PopularMoviesFragment> {
            sut = this as PopularMoviesFragment
        }
        
        moviesSharedFlow.emit(listOf(PopularMoviesUIData("1", "path1"), PopularMoviesUIData("2", "path2")))
        assert(sut.moviesListDataVisibleForTest[0].id == "1")
        assert(sut.moviesListDataVisibleForTest[0].posterPath == "path1")
        assert(sut.moviesListDataVisibleForTest[1].id == "2")
        assert(sut.moviesListDataVisibleForTest[1].posterPath == "path2")

    }

    @Module
    @InstallIn(FragmentComponent::class)
    class TestHiltModule {

        //val viewModel = mockViewModel

        @PopularMoviesScope
        @Provides
        fun provideViewModelFactory(
            requestPopularMoviesUseCase: IRequestPopularMoviesUseCase,
            observePopularMoviesDataUseCase: IObservePopularMoviesDataUseCase
        ): ViewModelProvider.Factory {
            return ViewModelFactory(
                requestPopularMoviesUseCase,
                observePopularMoviesDataUseCase
            )
        }

        class ViewModelFactory @Inject constructor(
            private val requestPopularMoviesUseCase: IRequestPopularMoviesUseCase,
            private val observePopularMoviesDataUseCase: IObservePopularMoviesDataUseCase
        ) : ViewModelProvider.Factory {
            override fun <T : ViewModel?> create(modelClass: Class<T>): T {
                return when (modelClass) {
                    PopularMoviesViewModel::class.java ->
                        mockViewModel as T
                    else -> modelClass.newInstance()
                }
            }
        }
    }

    companion object {
        val mockViewModel = mockk<PopularMoviesViewModel>(relaxed = true)
    }
}

