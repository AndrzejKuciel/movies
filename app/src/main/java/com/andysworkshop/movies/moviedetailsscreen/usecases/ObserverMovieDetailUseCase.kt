package com.andysworkshop.movies.moviedetailsscreen.usecases

import com.andysworkshop.movies.domain.IStore
import com.andysworkshop.movies.domain.data.MovieDetailRequestResult
import com.andysworkshop.movies.moviedetailsscreen.data.MovieDetailUIData
import com.andysworkshop.movies.moviedetailsscreen.data.MovieDetailUIDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ObserverMovieDetailUseCase @Inject constructor(
    private val store: IStore
) : IObserverMovieDetailUseCase {

    private val _movieDetailUIDataFlow = MutableSharedFlow<MovieDetailUIDataResult>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override fun invoke(scope: CoroutineScope): SharedFlow<MovieDetailUIDataResult> {
        setupStoreMovieDetailsObserver(scope)
        return _movieDetailUIDataFlow
    }

    private fun setupStoreMovieDetailsObserver(scope: CoroutineScope) {
        store.moviesDetailData.onEach { movieDetailRequestResult ->
            when(movieDetailRequestResult) {
                is MovieDetailRequestResult.Success -> {
                    _movieDetailUIDataFlow.tryEmit(
                        MovieDetailUIDataResult.Success(
                            MovieDetailUIData(
                                title = movieDetailRequestResult.value.title,
                                overview = movieDetailRequestResult.value.overview,
                                homepage = movieDetailRequestResult.value.homepage,
                                releaseDate = movieDetailRequestResult.value.releaseDate,
                                voteAverage = movieDetailRequestResult.value.voteAverage
                            )
                        )
                    )
                }
                is MovieDetailRequestResult.Error -> {
                    _movieDetailUIDataFlow.tryEmit(
                        MovieDetailUIDataResult.Error(movieDetailRequestResult.message)
                    )
                }
            }
        }.launchIn(scope)
    }
}