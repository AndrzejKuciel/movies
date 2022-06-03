package com.andysworkshop.movies.popularmoviesscreen.usecases

import com.andysworkshop.movies.domain.IStore
import javax.inject.Inject

class RequestPopularMoviesUseCase @Inject constructor(
    private val store: IStore
) : IRequestPopularMoviesUseCase {
    override suspend fun invoke(maxNumberOfMovies: Int) {
        store.requestPopularMoviesImages(maxNumberOfMovies)
    }
}