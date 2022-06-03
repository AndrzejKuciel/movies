package com.andysworkshop.movies.networking

import com.andysworkshop.movies.networking.dto.MovieDetailResponseDto
import com.andysworkshop.movies.networking.dto.PopularMoviesResponseDto
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface IRetrofitApiInterface {

    @GET("3/movie/popular?api_key=$API_KEY")
    suspend fun getPopularMovies(): PopularMoviesResponseDto

    @GET("3/movie/{movieId}?api_key=$API_KEY")
    suspend fun getMovieDetails(@Path(value="movieId") movieId: String): MovieDetailResponseDto

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/"

        // TODO: use JNI to hide key
        private const val API_KEY = "f862fc77de34f3840249571170ad5a31"

        fun create(): IRetrofitApiInterface {
            val retrofit = Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()

            return retrofit.create(IRetrofitApiInterface::class.java)
        }
    }
}