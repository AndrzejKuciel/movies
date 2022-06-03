package com.andysworkshop.movies.domain.data

data class MovieDetailData(
    val title: String,
    val overview: String,
    var homepage: String,
    var releaseDate: String,
    var voteAverage: Double,
)