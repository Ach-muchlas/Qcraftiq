package com.am.finalproject.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.R
import com.am.finalproject.data.Course
import com.am.finalproject.databinding.ItemPopularCourseBinding

class HomePopularCourseAdapter :
    ListAdapter<Course, HomePopularCourseAdapter.MyViewHolder>(DIFF_CALLBACK) {
    inner class MyViewHolder(private val binding: ItemPopularCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Course) {
            val buttonText = binding.root.context.getString(R.string.buy) + data.price
            binding.textViewTagLineCategory.text = data.tagLine
            binding.imageContent.setImageResource(data.imageUrl)
            binding.textViewTitleCourse.text = data.title
            binding.textViewRate.text = data.rate.toString()
            binding.textViewMentor.text = data.mentor
            binding.textViewLevelCourse.text = data.level
            binding.textViewModule.text = data.module
            binding.textViewTime.text = data.time
            binding.buttonBuy.text = buttonText
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemPopularCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataPopularCourse = getItem(position)
        holder.bind(dataPopularCourse)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Course>() {
            override fun areItemsTheSame(
                oldItem: Course,
                newItem: Course
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Course,
                newItem: Course
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}