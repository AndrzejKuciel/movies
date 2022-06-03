package com.andysworkshop.movies.popularmoviesscreen.data

sealed class PopularMoviesUIDataResult {
    data class Success(val value: List<PopularMoviesUIData>): PopularMoviesUIDataResult()
    data class Error(val message: String): PopularMoviesUIDataResult()
}
