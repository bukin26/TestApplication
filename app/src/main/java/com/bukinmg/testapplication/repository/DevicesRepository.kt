package com.bukinmg.testapplication.repository

import com.bukinmg.testapplication.data.local.Device
import com.bukinmg.testapplication.data.local.DeviceDao
import com.bukinmg.testapplication.data.remote.DevicesService
import com.bukinmg.testapplication.data.Result
import com.bukinmg.testapplication.data.wrapResult
import kotlinx.coroutines.flow.Flow
import java.lang.RuntimeException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DevicesRepository @Inject constructor(
    private val service: DevicesService,
    private val dao: DeviceDao
) {

    suspend fun fetchDevices(): Result<List<Device>> {
        return wrapResult {
            val result = service.getDevices()
            if (!result.isSuccessful
                || result.body() == null
                || result.body()?.devices.isNullOrEmpty()
            ) throw RuntimeException()
            val devices = result.body()?.devices?.mapIndexed { index, deviceDto ->
                deviceDto.toDevice(
                    index
                )
            } ?: throw RuntimeException()
            dao.insertDevices(devices)
            return@wrapResult devices
        }
    }

    fun getDevices(): Flow<List<Device>> {
        return dao.getDevices()
    }

    suspend fun getDevice(serialNumber: Int): Result<Device> {
        return wrapResult {
            dao.getDevice(serialNumber)
        }

    }

    suspend fun deleteDevice(serialNumber: Int): Result<Unit> {
        return wrapResult {
            dao.deleteDevice(serialNumber)
        }
    }

    suspend fun updateDevice(device: Device): Result<Unit> {
        return wrapResult {
            dao.updateDevice(device)
        }
    }
}