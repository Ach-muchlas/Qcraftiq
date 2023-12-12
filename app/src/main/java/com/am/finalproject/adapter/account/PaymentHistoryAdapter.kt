package com.am.finalproject.adapter.account

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.R
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.databinding.ItemClassCourseBinding
import com.bumptech.glide.Glide

class PaymentHistoryAdapter :
    ListAdapter<DataItemCourse, PaymentHistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    inner class MyViewHolder(private val binding: ItemClassCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: DataItemCourse) {
            Glide.with(binding.root.context).load(data.image).into(binding.imageContent)
            binding.textViewTagLineCategory.text = data.category.title
            binding.textViewTitleCourse.text = data.title
            binding.textViewRate.text = data.rating.toString()
            binding.textViewMentor.text = data.authorBy
            binding.textViewLevelCourse.text = data.level
            binding.textViewContentCard.text = data.type

            val textMenit = " Menit"
            val textModule = " Modul"
            if (!data.module.isNullOrEmpty()) {
                val totalTime = data.module.sumOf { it.time ?: 0 }
                val totalModule = "${data.module.size}"

                binding.textViewTime.text = "$totalTime $textMenit"
                binding.textViewModule.text = "$totalModule $textModule"
            } else {
                binding.textViewModule.text = "0 $textModule"
                binding.textViewTime.text = "0 $textMenit"
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemCourse>() {
            override fun areItemsTheSame(
                oldItem: DataItemCourse,
                newItem: DataItemCourse
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemCourse,
                newItem: DataItemCourse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}