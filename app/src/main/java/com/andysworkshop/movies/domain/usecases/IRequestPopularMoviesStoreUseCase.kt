package com.andysworkshop.movies.domain.usecases

import com.andysworkshop.movies.domain.data.PopularMoviesRequestResult

interface IRequestPopularMoviesStoreUseCase {
    suspend fun invoke(): PopularMoviesRequestResult
}