package com.am.finalproject.adapter.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.remote.DataItemHistory
import com.am.finalproject.data.remote.DataItemModule
import com.am.finalproject.databinding.ItemClassCourseBinding
import com.am.finalproject.utils.Formatter
import com.bumptech.glide.Glide

class PaymentHistoryAdapter :
    ListAdapter<DataItemHistory, PaymentHistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    inner class MyViewHolder(private val binding: ItemClassCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataItemHistory) {
            binding.textViewContentCard.text = data.status
            data.course?.let { course ->
                Glide.with(binding.root.context).load(course.image).into(binding.imageContent)
                binding.textViewTagLineCategory.text = course.category.title
                binding.textViewTitleCourse.text = course.title
                binding.textViewRate.text = course.rating.toString()
                binding.textViewMentor.text = course.authorBy
                binding.textViewLevelCourse.text = course.level

                if (!course.module.isNullOrEmpty()) {
                    binding.textViewTime.text =
                        Formatter.formatTimeSecondToMinute(course.module.sumOf { module: DataItemModule ->
                            module.time ?: 0
                        })
                    binding.textViewModule.text = Formatter.formatSizeModule(course.module.size)
                } else {
                    binding.textViewModule.text = Formatter.formatSizeModule(0)
                    binding.textViewTime.text = Formatter.formatTimeSecondToMinute(0)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemClassCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        holder.bind(data)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemHistory>() {
            override fun areItemsTheSame(
                oldItem: DataItemHistory,
                newItem: DataItemHistory
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemHistory,
                newItem: DataItemHistory
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}