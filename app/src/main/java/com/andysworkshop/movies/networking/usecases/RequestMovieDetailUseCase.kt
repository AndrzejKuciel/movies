package com.andysworkshop.movies.networking.usecases

import com.andysworkshop.movies.domain.data.MovieDetailRequestResult
import com.andysworkshop.movies.domain.usecases.IRequestMovieDetailStoreUseCase
import com.andysworkshop.movies.networking.INetwork
import javax.inject.Inject

class RequestMovieDetailUseCase @Inject constructor(private val network: INetwork): IRequestMovieDetailStoreUseCase {
    override suspend fun invoke(movieId: String): MovieDetailRequestResult {
        return network.requestMovieDetail(movieId)
    }
}