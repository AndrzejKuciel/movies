package com.andysworkshop.movies.popularmoviesscreen.usecases

import com.andysworkshop.movies.domain.IStore
import com.andysworkshop.movies.domain.data.PopularMoviesRequestResult
import com.andysworkshop.movies.popularmoviesscreen.data.PopularMoviesUIData
import com.andysworkshop.movies.popularmoviesscreen.data.PopularMoviesUIDataResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class ObservePopularMoviesDataUseCase @Inject constructor(
    private val store: IStore
) : IObservePopularMoviesDataUseCase {

    private val _popularMoviesUIDataFlow = MutableSharedFlow<PopularMoviesUIDataResult>(
        extraBufferCapacity = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )

    override fun invoke(scope: CoroutineScope): SharedFlow<PopularMoviesUIDataResult> {
        setupStorePopularMoviesObserver(scope)
        return _popularMoviesUIDataFlow
    }

    private fun setupStorePopularMoviesObserver(scope: CoroutineScope) {
        store.popularMoviesData.onEach { popularMoviesRequestResult ->
            when (popularMoviesRequestResult) {
                is PopularMoviesRequestResult.Success -> {
                    println("Use case successfully got movies data")
                    popularMoviesRequestResult.value.map {
                        PopularMoviesUIData(
                            id = it.id,
                            posterPath = it.posterPath
                        )
                    }.also {
                        _popularMoviesUIDataFlow.tryEmit(PopularMoviesUIDataResult.Success(it))
                    }
                }
                is PopularMoviesRequestResult.Error -> {
                    _popularMoviesUIDataFlow.tryEmit(
                        PopularMoviesUIDataResult.Error(popularMoviesRequestResult.message)
                    )
                }
            }
        }.launchIn(scope)
    }
}