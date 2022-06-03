package com.andysworkshop.movies.domain

import com.andysworkshop.movies.domain.data.MovieDetailRequestResult
import com.andysworkshop.movies.domain.data.PopularMoviesRequestResult
import kotlinx.coroutines.flow.SharedFlow

interface IStore {
    val popularMoviesData: SharedFlow<PopularMoviesRequestResult>
    val moviesDetailData: SharedFlow<MovieDetailRequestResult>

    suspend fun requestPopularMoviesImages(maxNumberOfMovies: Int)

    suspend fun requestMovieDetail(movieId: String)
}