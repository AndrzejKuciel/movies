package com.andysworkshop.movies.popularmoviesscreen.usecases

import com.andysworkshop.movies.popularmoviesscreen.data.PopularMoviesUIDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow

interface IObservePopularMoviesDataUseCase {
    fun invoke(scope: CoroutineScope): SharedFlow<PopularMoviesUIDataResult>
}