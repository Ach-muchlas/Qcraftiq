package com.am.finalproject.adapter.classroom

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.remote.DataItemTrackingClass
import com.am.finalproject.databinding.ItemPopularCourseBinding
import com.am.finalproject.utils.Formatter
import com.bumptech.glide.Glide

class CourseTrackIngAdapter : ListAdapter<DataItemTrackingClass, CourseTrackIngAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    var callBackToDetail: ((String) -> Unit)? = null
    inner class MyViewHolder(val binding: ItemPopularCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(data: DataItemTrackingClass) {
            val timeModule = data.course.module?.sumOf { it.time ?: 0 }
            val sizeModule = data.course.module?.size
            var progress = 0

            Glide.with(binding.root.context).load(data.course.image).into(binding.imageContent)
            binding.textViewTagLineCategory.text = data.course.category.title
            binding.textViewRating.text = data.course.rating.toString()
            binding.textViewTitleCourse.text = data.course.title
            binding.textViewMentor.text = data.course.authorBy
            binding.textViewLevelCourse.text = data.course.level
            binding.textViewModule.text = Formatter.formatSizeModule(sizeModule ?: 0)
            binding.textViewTime.text = Formatter.formatTimeSecondToMinute(timeModule ?: 0)
            binding.buttonBuyPopular.visibility = View.GONE
            binding.iconProgress.visibility = View.VISIBLE
            when (data.status.toString()) {
                "DONE" -> {
                    progress = 100
                    binding.textViewProgressStatus.text = "${progress}% complete"
                    binding.progressBar.setProgress(progress)
                }

                "PROGRESS" -> {
                    progress = 50
                    binding.textViewProgressStatus.text = "${progress}% complete"
                    binding.progressBar.setProgress(progress)
                }
            }

            binding.card.setOnClickListener {
                callBackToDetail?.invoke(data.courseId.toString())
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