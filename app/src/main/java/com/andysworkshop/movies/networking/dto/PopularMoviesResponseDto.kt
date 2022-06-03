package com.andysworkshop.movies.networking.dto

import com.google.gson.annotations.SerializedName

data class PopularMoviesResponseDto(
    @SerializedName("results")
    var results: List<MoviesDto>
)