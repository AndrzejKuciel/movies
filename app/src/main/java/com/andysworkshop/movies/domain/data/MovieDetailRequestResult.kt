package com.andysworkshop.movies.domain.data

sealed class MovieDetailRequestResult {
    data class Success(val value: MovieDetailData): MovieDetailRequestResult()
    data class Error(val message: String): MovieDetailRequestResult()
}