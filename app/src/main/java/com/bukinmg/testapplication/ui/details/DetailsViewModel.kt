package com.bukinmg.testapplication.ui.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bukinmg.testapplication.data.local.Device
import com.bukinmg.testapplication.data.Result
import com.bukinmg.testapplication.repository.DevicesRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailsViewModel constructor(
    private val repository: DevicesRepository,
    private val serialNumber: Int
) : ViewModel() {

    private val _deviceFlow: MutableStateFlow<Device> = MutableStateFlow(Device())
    val deviceFlow: StateFlow<Device> = _deviceFlow

    init {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getDevice(serialNumber)
            if (result is Result.Success) {
                _deviceFlow.emit(result.data)
            }
        }
    }
}