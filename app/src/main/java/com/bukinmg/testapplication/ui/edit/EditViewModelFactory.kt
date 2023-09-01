package com.bukinmg.testapplication.ui.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bukinmg.testapplication.repository.DevicesRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject

@Suppress("UNCHECKED_CAST")
class EditViewModelFactory @AssistedInject constructor(
    private val repository: DevicesRepository,
    @Assisted("serialNumber") private val serialNumber: Int
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            EditViewModel::class.java -> EditViewModel(repository, serialNumber) as T
            else -> throw IllegalArgumentException("Unknown ViewModel Class")
        }
    }

    @AssistedFactory
    interface Factory {

        fun create(@Assisted("serialNumber") serialNumber: Int): EditViewModelFactory
    }
}