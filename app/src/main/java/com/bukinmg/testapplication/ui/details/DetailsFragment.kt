package com.bukinmg.testapplication.ui.details

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.bukinmg.testapplication.R
import com.bukinmg.testapplication.databinding.FragmentDetailsBinding
import com.bukinmg.testapplication.utils.toImageResId
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()

    @Inject
    lateinit var viewModelFactory: DetailsViewModelFactory.Factory
    private val viewModel: DetailsViewModel by viewModels(factoryProducer = {
        viewModelFactory.create(
            args.serialNumber
        )
    })

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycleScope.launch {
            viewModel.deviceFlow.collect {
                with(binding) {
                    deviceTitleText.text = it.title
                    deviceSerialNumberText.text =
                        resources.getString(R.string.serialNumber, it.serialNumber)
                    deviceMacAddressText.text =
                        resources.getString(R.string.macAddress, it.macAddress)
                    deviceFirmwareText.text = resources.getString(R.string.firmware, it.firmware)
                    deviceModelText.text = resources.getString(R.string.model, it.model)
                    deviceImage.setImageResource(it.model.toImageResId())
                }
            }
        }
    }
}