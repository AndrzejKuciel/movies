package com.andysworkshop.movies.networking.di

import com.andysworkshop.movies.networking.INetwork
import com.andysworkshop.movies.networking.IRetrofitApiInterface
import com.andysworkshop.movies.networking.Network
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Singleton
    @Provides
    fun provideNetwork(retrofitApiInterface: IRetrofitApiInterface): INetwork {
        return Network(retrofitApiInterface)
    }

    @Provides
    fun provideRetrofitApiInterface(): IRetrofitApiInterface {
        return IRetrofitApiInterface.create()
    }
}