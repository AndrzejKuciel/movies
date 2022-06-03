package com.andysworkshop.movies.popularmoviesscreen.di

import com.andysworkshop.movies.popularmoviesscreen.usecases.IObservePopularMoviesDataUseCase
import com.andysworkshop.movies.popularmoviesscreen.usecases.IRequestPopularMoviesUseCase
import com.andysworkshop.movies.popularmoviesscreen.usecases.ObservePopularMoviesDataUseCase
import com.andysworkshop.movies.popularmoviesscreen.usecases.RequestPopularMoviesUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class PopularMoviesUseCasesModule {
    @Binds
    abstract fun bindRequestPopularMoviesUseCase(
        requestPopularMoviesUseCase: RequestPopularMoviesUseCase
    ): IRequestPopularMoviesUseCase

    @Binds
    abstract fun bindObservePopularMoviesDataUseCase(
        observePopularMoviesDataUseCase: ObservePopularMoviesDataUseCase
    ): IObservePopularMoviesDataUseCase
}