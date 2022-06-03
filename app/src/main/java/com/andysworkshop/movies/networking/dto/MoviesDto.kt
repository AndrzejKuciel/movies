package com.andysworkshop.movies.networking.dto

import com.google.gson.annotations.SerializedName

data class MoviesDto(
    @SerializedName("id")
    var id: String,
    @SerializedName("poster_path")
    var posterPath: String,
    @SerializedName("popularity")
    var popularity: String
)