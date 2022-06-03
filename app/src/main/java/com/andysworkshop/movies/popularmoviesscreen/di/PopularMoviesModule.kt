package com.andysworkshop.movies.popularmoviesscreen.di

import androidx.fragment.app.Fragment
import com.andysworkshop.movies.MainActivity
import com.andysworkshop.movies.popularmoviesscreen.PopularMoviesFragment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent


@Module
@InstallIn(ActivityComponent::class)
abstract class PopularMoviesModule {
    @PopularMoviesScope
    @Binds
    abstract fun bindPopularMoviesFragment(@PopularMoviesScope popularMoviesFragment: PopularMoviesFragment): Fragment
}