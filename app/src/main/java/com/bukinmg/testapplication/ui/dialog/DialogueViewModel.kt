package com.bukinmg.testapplication.ui.dialog

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bukinmg.testapplication.repository.DevicesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DialogueViewModel @Inject constructor(
    private val repository: DevicesRepository,
) : ViewModel() {

    fun deleteDevice(serialNumber: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteDevice(serialNumber)
        }
    }
}