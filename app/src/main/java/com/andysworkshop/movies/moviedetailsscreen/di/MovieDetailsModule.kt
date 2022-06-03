package com.andysworkshop.movies.moviedetailsscreen.di

import android.app.Activity
import androidx.fragment.app.Fragment
import com.andysworkshop.movies.MainActivity
import com.andysworkshop.movies.moviedetailsscreen.MovieDetailFragment
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
abstract class MovieDetailsModule {
    @MovieDetailScope
    @Binds
    abstract fun bindMovieDetailFragment(@MovieDetailScope movieDetailFragment: MovieDetailFragment): Fragment
}