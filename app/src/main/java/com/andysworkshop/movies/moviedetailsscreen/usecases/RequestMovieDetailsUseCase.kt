package com.andysworkshop.movies.moviedetailsscreen.usecases

import com.andysworkshop.movies.domain.IStore
import javax.inject.Inject

class RequestMovieDetailsUseCase @Inject constructor(private val store: IStore): IRequestMovieDetailUseCase{
    override suspend fun invoke(movieId: String) {
        store.requestMovieDetail(movieId)
    }
}