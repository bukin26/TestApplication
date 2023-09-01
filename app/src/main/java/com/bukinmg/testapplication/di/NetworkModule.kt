package com.bukinmg.testapplication.di

import com.bukinmg.testapplication.data.remote.DevicesService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideDevicesService(): DevicesService {
        return DevicesService.create()
    }
}