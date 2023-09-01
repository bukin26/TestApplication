package com.bukinmg.testapplication.data.remote

import com.bukinmg.testapplication.data.local.Device
import com.bukinmg.testapplication.utils.toModel
import com.google.gson.annotations.SerializedName

data class DeviceDto(

    @field:SerializedName("PK_Device") val serialNumber: Int,
    @field:SerializedName("MacAddress") val macAddress: String?,
    @field:SerializedName("Firmware") val firmware: String?,
    @field:SerializedName("Platform") val platform: String?
) {
    fun toDevice(index: Int): Device {
        return Device(
            title = "Home Number ${index + 1}",
            serialNumber = this.serialNumber,
            macAddress = this.macAddress ?: "",
            firmware = this.firmware ?: "",
            model = this.platform.toModel(),
        )
    }

}
