package com.andysworkshop.movies.moviedetailsscreen.di

import com.andysworkshop.movies.moviedetailsscreen.usecases.IObserverMovieDetailUseCase
import com.andysworkshop.movies.moviedetailsscreen.usecases.IRequestMovieDetailUseCase
import com.andysworkshop.movies.moviedetailsscreen.usecases.ObserverMovieDetailUseCase
import com.andysworkshop.movies.moviedetailsscreen.usecases.RequestMovieDetailsUseCase
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
abstract class MovieDetailUseCasesModule {
    @Binds
    abstract fun bindRequestMovieDetailsUseCase(
        requestMovieDetailUseCase: RequestMovieDetailsUseCase
    ): IRequestMovieDetailUseCase

    @Binds
    abstract fun bindObserverMovieDetailUseCase(
        observerMovieDetailUseCase: ObserverMovieDetailUseCase
    ): IObserverMovieDetailUseCase
}