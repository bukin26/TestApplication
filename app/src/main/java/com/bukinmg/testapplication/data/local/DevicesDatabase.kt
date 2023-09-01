package com.bukinmg.testapplication.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [Device::class], version = 1, exportSchema = false)
abstract class DevicesDatabase : RoomDatabase() {

    abstract fun devicesDao(): DeviceDao
}