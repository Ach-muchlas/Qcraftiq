package com.am.finalproject.adapter.notification

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.Notification
import com.am.finalproject.databinding.ItemNotificationBinding
import com.am.finalproject.utils.Formatter

class NotificationAdapter : ListAdapter<Notification, NotificationAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    inner class MyViewHolder(private val binding: ItemNotificationBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Notification) {
            binding.textViewTagLineNotification.text = data.tagLine
            binding.textViewTitleNotification.text = data.title
            binding.textViewDescriptionNotification.text = data.description
            binding.textViewTimeNowNotification.text = data.time
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Notification>() {
            override fun areItemsTheSame(
                oldItem: Notification,
                newItem: Notification
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Notification,
                newItem: Notification
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}