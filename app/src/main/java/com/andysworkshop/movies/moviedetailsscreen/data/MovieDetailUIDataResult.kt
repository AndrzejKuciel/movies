package com.andysworkshop.movies.moviedetailsscreen.data

sealed class MovieDetailUIDataResult {
    data class Success(val value: MovieDetailUIData) : MovieDetailUIDataResult()
    data class Error(val message: String) : MovieDetailUIDataResult()
}
