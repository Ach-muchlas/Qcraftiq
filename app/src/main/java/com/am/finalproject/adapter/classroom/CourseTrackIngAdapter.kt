package com.am.finalproject.adapter.classroom

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.databinding.ItemPopularCourseBinding
import com.bumptech.glide.Glide

class CourseTrackIngAdapter : ListAdapter<DataItemCourse, CourseTrackIngAdapter.MyViewHolder>(
    DIFF_CALLBACK
) {
    inner class MyViewHolder(val binding: ItemPopularCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemCourse) {
            val author = "By " + data.authorBy
            Glide.with(binding.root.context).load(data.image).into(binding.imageContent)
            binding.textViewTagLineCategory.text = data.category.title
            binding.textViewRating.text = data.rating.toString()
            binding.textViewTitleCourse.text = data.title
            binding.textViewMentor.text = author
            binding.textViewLevelCourse.text = data.level
            binding.textViewTime.text = data.rating.toString()
            binding.buttonBuy.visibility = View.GONE
            binding.textViewProgressStatus.text = data.rating.toString()
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