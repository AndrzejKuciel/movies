package com.andysworkshop.movies.moviedetailsscreen.usecases

import com.andysworkshop.movies.domain.data.MovieDetailRequestResult
import com.andysworkshop.movies.moviedetailsscreen.data.MovieDetailUIDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharedFlow

interface IObserverMovieDetailUseCase {
    fun invoke(scope: CoroutineScope): SharedFlow<MovieDetailUIDataResult>
}