package com.andysworkshop.movies.networking

import com.andysworkshop.movies.domain.data.MovieData
import com.andysworkshop.movies.domain.data.MovieDetailData
import com.andysworkshop.movies.domain.data.MovieDetailRequestResult
import com.andysworkshop.movies.domain.data.PopularMoviesRequestResult
import javax.inject.Inject

class Network @Inject constructor(
    private val retrofitApiInterface: IRetrofitApiInterface
) : INetwork {

    override suspend fun requestPopularMoviesImages(): PopularMoviesRequestResult {
        return try {
            PopularMoviesRequestResult.Success(
                retrofitApiInterface.getPopularMovies().results.map {
                    MovieData(
                        id = it.id,
                        popularity = it.popularity,
                        posterPath = POSTER_BASE_PATH + it.posterPath
                    )
                }
            )
        } catch (error: Throwable) {
            PopularMoviesRequestResult.Error(error.message ?: "Unknown error")
        }
    }

    override suspend fun requestMovieDetail(movieId: String): MovieDetailRequestResult {
        return try {
            val movieDetailsDto = retrofitApiInterface.getMovieDetails(movieId)
            MovieDetailRequestResult.Success(
                MovieDetailData(
                    overview = movieDetailsDto.overview,
                    title = movieDetailsDto.title,
                    homepage = movieDetailsDto.homepage,
                    releaseDate = movieDetailsDto.releaseDate,
                    voteAverage = movieDetailsDto.voteAverage.toDouble(),
                )
            )
        } catch (error: Throwable) {
            MovieDetailRequestResult.Error(error.message ?: "Unknown error")
        }
    }

    companion object {
        private const val POSTER_BASE_PATH = "https://image.tmdb.org/t/p/original"
    }
}