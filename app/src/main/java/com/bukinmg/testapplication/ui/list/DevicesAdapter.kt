package com.bukinmg.testapplication.ui.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bukinmg.testapplication.R
import com.bukinmg.testapplication.data.local.Device
import com.bukinmg.testapplication.databinding.ItemDeviceListBinding
import com.bukinmg.testapplication.utils.toImageResId

class DevicesAdapter(
    private val onClick: (Device) -> Unit,
    private val onLongClick: (Device) -> Unit,
    private val onEditButtonClick: (Device) -> Unit,
) : ListAdapter<Device, DevicesAdapter.DevicesViewHolder>(ItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DevicesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return DevicesViewHolder(ItemDeviceListBinding.inflate(inflater, parent, false))
    }

    override fun onBindViewHolder(holder: DevicesViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item, onClick, onLongClick, onEditButtonClick)
    }

    class DevicesViewHolder(
        private val binding: ItemDeviceListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun bind(
            device: Device,
            onClick: (Device) -> Unit,
            onLongClick: (Device) -> Unit,
            onEditButtonClick: (Device) -> Unit
        ) {
            with(binding) {
                deviceTitleText.text = device.title
                deviceSerialNumberText.text =
                    itemView.resources.getString(R.string.serialNumber, device.serialNumber)
                deviceImage.setImageResource(device.model.toImageResId())
                root.setOnClickListener { onClick(device) }
                root.setOnLongClickListener {
                    onLongClick(device)
                    return@setOnLongClickListener true
                }
                editeButton.setOnClickListener {
                    onEditButtonClick(device)
                }
            }
        }
    }
}

object ItemDiffCallback : DiffUtil.ItemCallback<Device>() {

    override fun areItemsTheSame(oldItem: Device, newItem: Device): Boolean {
        return oldItem.serialNumber == newItem.serialNumber
    }

    override fun areContentsTheSame(oldItem: Device, newItem: Device): Boolean {
        return oldItem == newItem
    }
}