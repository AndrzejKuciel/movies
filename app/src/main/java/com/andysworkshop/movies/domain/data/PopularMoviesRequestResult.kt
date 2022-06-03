package com.andysworkshop.movies.domain.data

sealed class PopularMoviesRequestResult {
    data class Success(val value: List<MovieData>): PopularMoviesRequestResult()
    data class Error(val message: String): PopularMoviesRequestResult()
}
