package com.am.finalproject.adapter.classroom

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.data.remote.DataItemTrackingClass
import com.am.finalproject.databinding.ItemPopularCourseBinding
import com.bumptech.glide.Glide

class CourseTrackIngAdapter : ListAdapter<DataItemTrackingClass, CourseTrackIngAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    inner class MyViewHolder(val binding: ItemPopularCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: DataItemTrackingClass) {
            val author = "By " + data.course?.authorBy
            val timeModule = data.course?.module?.sumOf { it.time ?: 0 }
            val sizeModule = data.course?.module?.size
            Glide.with(binding.root.context).load(data.course?.image).into(binding.imageContent)
            binding.textViewTagLineCategory.text = data.course?.category?.title
            binding.textViewRating.text = data.course?.rating.toString()
            binding.textViewTitleCourse.text = data.course?.title
            binding.textViewMentor.text = author
            binding.textViewLevelCourse.text = data.course?.level
            binding.textViewModule.text = "$sizeModule Modul"
            binding.textViewTime.text = "$timeModule Menit"
            binding.buttonBuy.visibility = View.GONE
            binding.iconProgress.visibility = View.VISIBLE
            val progress = 0

            when (data.status.toString()) {
                "DONE" -> {
                    progress.plus(10)
                    binding.textViewProgressStatus.text = "${progress}% complete"
                    binding.progressBar.setProgress(progress.plus(10))
                }
                "PROGRESS" -> {
                    binding.textViewProgressStatus.text = "${progress.plus(5)}% complete"
                    binding.progressBar.setProgress(progress.plus(5))
                }
                else -> {
                    binding.textViewProgressStatus.text = "${progress}% complete"
                    binding.progressBar.setProgress(0)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemPopularCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
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