package com.bukinmg.testapplication.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bukinmg.testapplication.data.local.Device
import com.bukinmg.testapplication.data.Result
import com.bukinmg.testapplication.repository.DevicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(
    private val repository: DevicesRepository
) : ViewModel() {

    private val _devicesFlow: MutableStateFlow<List<Device>> = MutableStateFlow(emptyList())
    val devicesFlow: StateFlow<List<Device>> = _devicesFlow

    private val errorChanel = Channel<Boolean>()
    val errorFlow = errorChanel.receiveAsFlow()

    init {
        fetchDevices()
        viewModelScope.launch {
            repository.getDevices().collect {
                _devicesFlow.value = it
            }
        }
    }

    fun fetchDevices() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.fetchDevices()
            if (result is Result.Error) {
                errorChanel.send(true)
            }

        }
    }
}