package com.andysworkshop.movies.networking.usecases

import com.andysworkshop.movies.domain.data.PopularMoviesRequestResult
import com.andysworkshop.movies.domain.usecases.IRequestPopularMoviesStoreUseCase
import com.andysworkshop.movies.networking.INetwork
import javax.inject.Inject

class RequestPopularMoviesUseCase @Inject constructor(
    private val network: INetwork
) : IRequestPopularMoviesStoreUseCase {
    override suspend fun invoke(): PopularMoviesRequestResult {
        return network.requestPopularMoviesImages()
    }
}