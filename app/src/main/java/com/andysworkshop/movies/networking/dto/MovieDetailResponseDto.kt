package com.andysworkshop.movies.networking.dto

import com.google.gson.annotations.SerializedName

data class MovieDetailResponseDto(
    @SerializedName("overview")
    var overview: String,
    @SerializedName("title")
    var title: String,
    @SerializedName("homepage")
    var homepage: String,
    @SerializedName("release_date")
    var releaseDate: String,
    @SerializedName("vote_average")
    var voteAverage: String,
)