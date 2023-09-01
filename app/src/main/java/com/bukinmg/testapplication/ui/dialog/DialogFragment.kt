package com.bukinmg.testapplication.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.bukinmg.testapplication.databinding.FragmentDialogBinding
import com.bukinmg.testapplication.ui.details.DetailsFragmentArgs
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DialogFragment : BottomSheetDialogFragment() {

    private val viewModel: DialogueViewModel by viewModels()
    private lateinit var binding: FragmentDialogBinding
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            okButton.setOnClickListener {
                viewModel.deleteDevice(args.serialNumber)
                NavHostFragment.findNavController(this@DialogFragment).popBackStack()
            }
            cancelButton.setOnClickListener {
                NavHostFragment.findNavController(this@DialogFragment).popBackStack()
            }
        }
    }
}