package com.am.finalproject.adapter.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.databinding.ItemPopularCourseBinding
import com.bumptech.glide.Glide

class HomePopularCourseAdapter :
    ListAdapter<DataItemCourse, HomePopularCourseAdapter.MyViewHolder>(DIFF_CALLBACK) {
    inner class MyViewHolder(private val binding: ItemPopularCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindContentPopularCourse(data: DataItemCourse) {
            val buttonText = "Buy      " + data.price
            binding.textViewTagLineCategory.text = data.category?.title
            binding.textViewTitleCourse.text = data.title
            Glide.with(binding.root.context).load(data.image).into(binding.imageContent)
            binding.textViewRate.text = data.rating.toString()
            binding.textViewMentor.text = data.authorBy
            binding.textViewLevelCourse.text = data.level
            binding.textViewModule.text = data.rating.toString()
            binding.textViewTime.text = data.rating.toString()
            binding.buttonBuy.text = buttonText
            binding.progressBar.visibility = View.GONE
            binding.textViewProgressStatus.visibility = View.GONE
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemPopularCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)

    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val data = getItem(position)
        holder.bindContentPopularCourse(data)
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
                oldItem: DataItemCourse, newItem: DataItemCourse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}