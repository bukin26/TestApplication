package com.bukinmg.testapplication.data.remote

import com.google.gson.annotations.SerializedName

data class DevicesResponse(
    @field:SerializedName("Devices") val devices: List<DeviceDto>?,
)
