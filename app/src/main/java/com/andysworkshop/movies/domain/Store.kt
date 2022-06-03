package com.andysworkshop.movies.domain

import android.util.Log
import com.andysworkshop.movies.domain.data.MovieDetailRequestResult
import com.andysworkshop.movies.domain.data.PopularMoviesRequestResult
import com.andysworkshop.movies.domain.usecases.IRequestMovieDetailStoreUseCase
import com.andysworkshop.movies.domain.usecases.IRequestPopularMoviesStoreUseCase
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import javax.inject.Inject

class Store @Inject constructor(
    private val requestPopularMoviesUseCase: IRequestPopularMoviesStoreUseCase,
    private val requestMovieDetailsUseCase: IRequestMovieDetailStoreUseCase
) : IStore {

    private val _popularMoviesData = MutableSharedFlow<PopularMoviesRequestResult>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _movieDetailsData = MutableSharedFlow<MovieDetailRequestResult>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override val popularMoviesData: SharedFlow<PopularMoviesRequestResult>
        get() {
            return _popularMoviesData
        }

    override val moviesDetailData: SharedFlow<MovieDetailRequestResult>
    get() {
        return _movieDetailsData
    }

    override suspend fun requestPopularMoviesImages(maxNumberOfMovies: Int) {
        Log.d(TAG, "Requested popular movies from store")
        when (val popularMoviesResult = requestPopularMoviesUseCase.invoke()) {
            is PopularMoviesRequestResult.Success -> {
                _popularMoviesData.tryEmit(PopularMoviesRequestResult.Success(
                    popularMoviesResult.value.sortedByDescending { it.popularity.toDouble() }
                        .subList(0, maxNumberOfMovies)).also {
                            println("Popular movies sorted and limited: $it")
                        }
                )
            }
            is PopularMoviesRequestResult.Error -> {
                _popularMoviesData.tryEmit(popularMoviesResult)
            }
        }
    }

    override suspend fun requestMovieDetail(movieId: String) {
        val movieDetails =  requestMovieDetailsUseCase.invoke(movieId)
        _movieDetailsData.tryEmit(movieDetails)
    }

    companion object {
        private const val TAG = "Store"
    }
}