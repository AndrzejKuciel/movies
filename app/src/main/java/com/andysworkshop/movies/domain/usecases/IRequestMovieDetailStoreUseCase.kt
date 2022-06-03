package com.andysworkshop.movies.domain.usecases

import com.andysworkshop.movies.domain.data.MovieDetailRequestResult

interface IRequestMovieDetailStoreUseCase {
    suspend fun invoke(movieId: String): MovieDetailRequestResult
}