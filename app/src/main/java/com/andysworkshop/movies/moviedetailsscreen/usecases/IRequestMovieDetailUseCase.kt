package com.andysworkshop.movies.moviedetailsscreen.usecases

interface IRequestMovieDetailUseCase {
    suspend fun invoke(movieId: String)
}