package com.andysworkshop.movies

import androidx.lifecycle.viewModelScope
import app.cash.turbine.test
import com.andysworkshop.movies.popularmoviesscreen.PopularMoviesViewModel
import com.andysworkshop.movies.popularmoviesscreen.data.PopularMoviesUIData
import com.andysworkshop.movies.popularmoviesscreen.usecases.IObservePopularMoviesDataUseCase
import com.andysworkshop.movies.popularmoviesscreen.usecases.IRequestPopularMoviesUseCase

import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class PopularMoviesViewModelUnitTest {

    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
    }

    @Test
    fun `test saveRecyclerPosition`() {

        val requestUseCaseMock = mockk<IRequestPopularMoviesUseCase>(relaxed = true)
        val observeUseCaseMock = mockk<IObservePopularMoviesDataUseCase>(relaxed = true)

        val sut = PopularMoviesViewModel(requestUseCaseMock, observeUseCaseMock)

        sut.saveRecyclerPosition(4)

        assertEquals(sut.recyclerViewPosition, 4)
    }

    @Test
    fun `test onPosterClicked`() = runTest {
        val requestUseCaseMock = mockk<IRequestPopularMoviesUseCase>(relaxed = true)
        val observeUseCaseMock = mockk<IObservePopularMoviesDataUseCase>(relaxed = true)

        val sut = PopularMoviesViewModel(requestUseCaseMock, observeUseCaseMock)

        val uiData = mockk<PopularMoviesUIData>(relaxed = true)

        sut.navigateMovieDetails.test {
            sut.onPosterClicked(uiData, 4)
            assertEquals(expectMostRecentItem(), uiData)
        }
        assertEquals(sut.recyclerViewPosition, 4)
    }

    @Test
    fun `test observePopularMoviesData invoked on init`() {

        val requestUseCaseMock = mockk<IRequestPopularMoviesUseCase>(relaxed = true)
        val observeUseCaseMock = mockk<IObservePopularMoviesDataUseCase>(relaxed = true)

        val sut = PopularMoviesViewModel(requestUseCaseMock, observeUseCaseMock)

        verify { observeUseCaseMock.invoke(sut.viewModelScope)  }
    }
}