package com.andysworkshop.movies.popularmoviesscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.andysworkshop.movies.popularmoviesscreen.data.PopularMoviesUIData
import com.andysworkshop.movies.popularmoviesscreen.data.PopularMoviesUIDataResult
import com.andysworkshop.movies.popularmoviesscreen.usecases.IObservePopularMoviesDataUseCase
import com.andysworkshop.movies.popularmoviesscreen.usecases.IRequestPopularMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

class PopularMoviesViewModel @Inject constructor(
    private val requestPopularMoviesUseCase: IRequestPopularMoviesUseCase,
    private val observePopularMoviesData: IObservePopularMoviesDataUseCase
) : ViewModel() {

    init {
        observeMovieDetailData()
        viewModelScope.launch(Dispatchers.IO) {
            requestPopularMoviesUseCase.invoke(TOP_NUMBER)
        }
    }

    private val _moviesSharedFlow = MutableSharedFlow<List<PopularMoviesUIData>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _navigateMovieDetails = MutableSharedFlow<PopularMoviesUIData>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private val _moviesRequestError = MutableSharedFlow<String>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    private var _recyclerViewPosition: Int = 0

    val moviesSharedFlow: SharedFlow<List<PopularMoviesUIData>>
        get() {
            return _moviesSharedFlow
        }

    val navigateMovieDetails: SharedFlow<PopularMoviesUIData> = _navigateMovieDetails

    val moviesRequestError: SharedFlow<String>
        get() {
            return _moviesRequestError
        }

    val recyclerViewPosition: Int
        get() {
            return _recyclerViewPosition
        }

    fun onPosterClicked(movieData: PopularMoviesUIData, position: Int) {
        println("View model on poster clicked: ${movieData.id}")
        _recyclerViewPosition = position
        _navigateMovieDetails.tryEmit(movieData)
    }

    fun saveRecyclerPosition(position: Int) {
        _recyclerViewPosition = position
    }

    private fun observeMovieDetailData() {
        observePopularMoviesData.invoke(viewModelScope).onEach { popularMoviesUIDataResult ->
            when (popularMoviesUIDataResult) {
                is PopularMoviesUIDataResult.Success -> {
                    println("View model successfully got movies data: ${popularMoviesUIDataResult.value}")
                    _moviesSharedFlow.tryEmit(popularMoviesUIDataResult.value)
                }
                is PopularMoviesUIDataResult.Error -> {
                    println("View model get movies data error: ${popularMoviesUIDataResult.message}")
                    _moviesRequestError.tryEmit(popularMoviesUIDataResult.message)
                }
            }
        }
            .flowOn(Dispatchers.IO)
            .launchIn(viewModelScope)
    }

    companion object {
        const val TOP_NUMBER = 10
    }
}