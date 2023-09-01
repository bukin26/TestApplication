package com.bukinmg.testapplication.di

import android.content.Context
import androidx.room.Room
import com.bukinmg.testapplication.data.local.DeviceDao
import com.bukinmg.testapplication.data.local.DevicesDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideDevicesDatabase(@ApplicationContext context: Context): DevicesDatabase {
        return Room.databaseBuilder(
            context,
            DevicesDatabase::class.java,
            "Devices.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDevicesDao(database: DevicesDatabase): DeviceDao {
        return database.devicesDao()
    }
}