package com.am.finalproject.adapter.notification

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.remote.DataItemNotification
import com.am.finalproject.databinding.ItemNotificationBinding
import com.am.finalproject.utils.Formatter
import java.util.TimeZone

class NotificationAdapter : ListAdapter<DataItemNotification, NotificationAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    inner class MyViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemNotification) {
            binding.textViewTagLineNotification.text = data.title
            binding.textViewTitleNotification.text = data.subtitle
            binding.textViewDescriptionNotification.text = data.description
            binding.textViewTimeNowNotification.text =
                Formatter.formatDate(data.updatedAt.toString(), TimeZone.getDefault().id)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemNotificationBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataNotification = getItem(position)
        holder.bind(dataNotification)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemNotification>() {
            override fun areItemsTheSame(
                oldItem: DataItemNotification,
                newItem: DataItemNotification
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemNotification,
                newItem: DataItemNotification
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}