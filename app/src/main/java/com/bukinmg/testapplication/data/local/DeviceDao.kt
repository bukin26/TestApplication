package com.bukinmg.testapplication.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface DeviceDao {

    @Query("SELECT * FROM devices ORDER BY serialNumber")
    fun getDevices(): Flow<List<Device>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertDevices(devices: List<Device>)

    @Query("DELETE FROM devices")
    suspend fun deleteAllDevices()

    @Query("SELECT * FROM devices WHERE serialNumber = :serialNumber")
    suspend fun getDevice(serialNumber: Int): Device

    @Query("DELETE FROM devices WHERE serialNumber = :serialNumber")
    suspend fun deleteDevice(serialNumber: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateDevice(device: Device)
}