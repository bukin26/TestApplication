package com.bukinmg.testapplication.ui.edit

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.bukinmg.testapplication.R
import com.bukinmg.testapplication.databinding.FragmentEditBinding
import com.bukinmg.testapplication.utils.toImageResId
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class EditFragment : Fragment() {

    private lateinit var binding: FragmentEditBinding
    private val args: EditFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: EditViewModelFactory.Factory
    private val viewModel: EditViewModel by viewModels(factoryProducer = {
        viewModelFactory.create(
            args.serialNumber
        )
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentEditBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.deviceFlow.collect {
                with(binding) {
                    deviceTitleText.setText(it.title)
                    deviceSerialNumberText.text =
                        resources.getString(R.string.serialNumber, it.serialNumber)
                    deviceMacAddressText.text =
                        resources.getString(R.string.macAddress, it.macAddress)
                    deviceFirmwareText.text = resources.getString(R.string.firmware, it.firmware)
                    deviceModelText.text = resources.getString(R.string.model, it.model)
                    deviceImage.setImageResource(it.model.toImageResId())
                    deviceTitleText.requestFocus()
                    deviceTitleText.setSelection(deviceTitleText.text.length);
                    val imm = getSystemService(requireContext(), InputMethodManager::class.java)
                    imm?.showSoftInput(deviceTitleText, InputMethodManager.SHOW_IMPLICIT)
                    saveChangesButton.setOnClickListener {
                        updateDevice(deviceTitleText.text.toString())
                    }
                }
            }
        }
    }

    private fun updateDevice(title: String) {
        viewModel.updateDevice(title)
        NavHostFragment.findNavController(this@EditFragment).popBackStack()
    }
}