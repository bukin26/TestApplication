package com.bukinmg.testapplication.ui.list

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.bukinmg.testapplication.R
import com.bukinmg.testapplication.data.local.Device
import com.bukinmg.testapplication.databinding.FragmentListBinding
import com.bukinmg.testapplication.utils.Constants
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.log

@AndroidEntryPoint
class ListFragment : Fragment() {

    private val viewModel: ListViewModel by viewModels()
    private lateinit var binding: FragmentListBinding
    private val adapter =
        DevicesAdapter(
            onClick = ::adapterOnClick,
            onLongClick = ::adapterOnLongClick,
            onEditButtonClick = ::adapterOnEditButtonClick
        )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            devicesList.layoutManager = LinearLayoutManager(context)
            devicesList.adapter = adapter
            devicesList.addItemDecoration(
                DividerItemDecoration(
                    requireContext(),
                    DividerItemDecoration.VERTICAL
                )
            )
            toolbar.inflateMenu(R.menu.item_actionbar_menu)
            toolbar.setOnMenuItemClickListener {
                when (it.itemId) {
                    R.id.action_reset -> {
                        resetButtonOnClick()
                        true
                    }

                    else -> false
                }
            }
        }
        lifecycleScope.launch {
            viewModel.devicesFlow.collect {
                adapter.submitList(it)
            }
        }
        lifecycleScope.launch {
            viewModel.errorFlow.collect {
                if (it) {
                    Snackbar.make(
                        binding.root,
                        resources.getString(R.string.fetchFailed),
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }
    }

    private fun adapterOnClick(device: Device) {
        val direction =
            ListFragmentDirections.actionListFragmentToDetailsFragment(device.serialNumber)
        NavHostFragment.findNavController(this@ListFragment).navigate(direction)
    }

    private fun adapterOnLongClick(device: Device) {
        val direction =
            ListFragmentDirections.actionListFragmentToDialogFragment(device.serialNumber)
        NavHostFragment.findNavController(this@ListFragment).navigate(direction)
    }

    private fun adapterOnEditButtonClick(device: Device) {
        val direction =
            ListFragmentDirections.actionListFragmentToEditFragment(device.serialNumber)
        NavHostFragment.findNavController(this@ListFragment).navigate(direction)
    }

    private fun resetButtonOnClick() {
        viewModel.fetchDevices()
    }
}