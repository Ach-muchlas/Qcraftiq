package com.am.finalproject.adapter.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.remote.DataItemModule
import com.am.finalproject.data.remote.DataItemTrackingClass
import com.am.finalproject.databinding.ItemClassCourseBinding
import com.am.finalproject.utils.Formatter
import com.bumptech.glide.Glide

class PaymentHistoryAdapter :
    ListAdapter<DataItemTrackingClass, PaymentHistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {

    inner class MyViewHolder(private val binding: ItemClassCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(data: DataItemTrackingClass) {
            Glide.with(binding.root.context).load(data.course.image).into(binding.imageContent)
            binding.textViewTagLineCategory.text = data.course.category.title
            binding.textViewTitleCourse.text = data.course.title
            binding.textViewRate.text = data.course.rating.toString()
            binding.textViewMentor.text = data.course.authorBy
            binding.textViewLevelCourse.text = data.course.level
            binding.textViewContentCard.text = data.status

            if (!data.course.module.isNullOrEmpty()) {
                binding.textViewTime.text =
                    Formatter.formatTimeSecondToMinute(data.course.module.sumOf { module: DataItemModule ->
                        module.time ?: 0
                    })
                binding.textViewModule.text = Formatter.formatSizeModule(data.course.module.size)
            } else {
                binding.textViewModule.text = Formatter.formatSizeModule(0)
                binding.textViewTime.text = Formatter.formatTimeSecondToMinute(0)
            }


//            val textMenit = " Menit"
//            val textModule = " Modul"
//            if (!data.module.isNullOrEmpty()) {
//                val totalTime = data.module.sumOf { it.time ?: 0 }
//                val totalModule = "${data.module.size}"
//
//                binding.textViewTime.text = "$totalTime $textMenit"
//                binding.textViewModule.text = "$totalModule $textModule"
//            } else {
//                binding.textViewModule.text = "0 $textModule"
//                binding.textViewTime.text = "0 $textMenit"
//            }
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemTrackingClass>() {
            override fun areItemsTheSame(
                oldItem: DataItemTrackingClass,
                newItem: DataItemTrackingClass
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemTrackingClass,
                newItem: DataItemTrackingClass
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}