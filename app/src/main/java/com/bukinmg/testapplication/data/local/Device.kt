package com.bukinmg.testapplication.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "devices")
data class Device(
    val title: String = "",
    @PrimaryKey val serialNumber: Int = -1,
    val macAddress: String = "",
    val firmware: String = "",
    val model: String = ""
)
