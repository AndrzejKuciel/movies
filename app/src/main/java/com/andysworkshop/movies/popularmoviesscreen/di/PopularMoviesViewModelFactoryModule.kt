package com.andysworkshop.movies.popularmoviesscreen.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.andysworkshop.movies.popularmoviesscreen.PopularMoviesViewModel
import com.andysworkshop.movies.popularmoviesscreen.usecases.IObservePopularMoviesDataUseCase
import com.andysworkshop.movies.popularmoviesscreen.usecases.IRequestPopularMoviesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.FragmentComponent
import javax.inject.Inject
import javax.inject.Singleton

@Module
@InstallIn(FragmentComponent::class)
class PopularMoviesViewModelFactoryModule {
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
                    PopularMoviesViewModel(
                        requestPopularMoviesUseCase,
                        observePopularMoviesDataUseCase
                    ) as T
                else -> modelClass.newInstance()
            }
        }
    }
}