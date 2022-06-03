package com.andysworkshop.movies.moviedetailsscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andysworkshop.movies.moviedetailsscreen.data.MovieDetailUIData
import com.andysworkshop.movies.moviedetailsscreen.data.MovieDetailUIDataResult
import com.andysworkshop.movies.moviedetailsscreen.usecases.IObserverMovieDetailUseCase
import com.andysworkshop.movies.moviedetailsscreen.usecases.IRequestMovieDetailUseCase
import com.andysworkshop.movies.popularmoviesscreen.data.PopularMoviesUIData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieDetailViewModel @Inject constructor(
    private val requestMovieDetailsUseCase: IRequestMovieDetailUseCase,
    private val observerMovieDetailUseCase: IObserverMovieDetailUseCase
) : ViewModel() {

    private val _movieDetailSharedFlow = MutableSharedFlow<MovieDetailUIData>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val movieDetailSharedFlow: SharedFlow<MovieDetailUIData>
        get() {
            return _movieDetailSharedFlow
        }

    private val _detailsRequestError = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val detailsRequestError: SharedFlow<String>
        get() {
            return _detailsRequestError
        }

    private val _navigationFlow = MutableSharedFlow<Unit>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    val navigationFlow: SharedFlow<Unit>
        get() {
            return _navigationFlow
        }


    fun onFragmentResumed(movieId: String) {
        observeMovieDetailData()

        viewModelScope.launch(Dispatchers.IO) {
            requestMovieDetailsUseCase.invoke(movieId)
        }
    }

    fun onScreenClick() {
        _navigationFlow.tryEmit(Unit)
    }

    private fun observeMovieDetailData() {
        observerMovieDetailUseCase.invoke(viewModelScope).onEach { movieDetailUIDataResult ->
            when (movieDetailUIDataResult) {
                is MovieDetailUIDataResult.Success -> {
                    println("View model successfully got movie detail: ${movieDetailUIDataResult.value}")
                    _movieDetailSharedFlow.tryEmit(movieDetailUIDataResult.value)
                }
                is MovieDetailUIDataResult.Error -> {
                    println("View model get movie detail error: ${movieDetailUIDataResult.message}")
                    _detailsRequestError.tryEmit(movieDetailUIDataResult.message)
                }
            }
        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }
}