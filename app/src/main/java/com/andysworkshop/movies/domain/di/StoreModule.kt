package com.andysworkshop.movies.domain.di

import com.andysworkshop.movies.domain.IStore
import com.andysworkshop.movies.domain.Store
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class StoreModule {
    @Singleton
    @Binds
    abstract fun bindStore(store: Store): IStore
}