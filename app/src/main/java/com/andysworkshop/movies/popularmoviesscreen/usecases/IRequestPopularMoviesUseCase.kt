package com.andysworkshop.movies.popularmoviesscreen.usecases

interface IRequestPopularMoviesUseCase {
    suspend fun invoke(maxNumberOfMovies: Int)
}