package com.andysworkshop.movies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andysworkshop.movies.popularmoviesscreen.PopularMoviesViewModel
import com.andysworkshop.movies.popularmoviesscreen.di.PopularMoviesScope
import com.andysworkshop.movies.popularmoviesscreen.di.PopularMoviesViewModelFactoryModule
import com.andysworkshop.movies.popularmoviesscreen.usecases.IObservePopularMoviesDataUseCase
import com.andysworkshop.movies.popularmoviesscreen.usecases.IRequestPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import javax.inject.Inject


@Module
@TestInstallIn(components = [FragmentComponent::class], replaces = [PopularMoviesViewModelFactoryModule::class])
class TestHiltModule {
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
                    mockk<PopularMoviesViewModel>(relaxed = true) as T
                else -> modelClass.newInstance()
            }
        }
    }
}
