package com.am.finalproject.adapter.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.databinding.ItemPopularCourseBinding
import com.am.finalproject.utils.Formatter
import com.bumptech.glide.Glide

class HomePopularCourseAdapter :
    ListAdapter<DataItemCourse, HomePopularCourseAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onClick: ((DataItemCourse) -> Unit)? = null

    inner class MyViewHolder(private val binding: ItemPopularCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindContentPopularCourse(data: DataItemCourse) {
            val author = "By " + data.authorBy
            binding.textViewTagLineCategory.text = data.category.title
            binding.textViewTitleCourse.text = data.title
            Glide.with(binding.root.context).load(data.image).into(binding.imageContent)
            binding.textViewRating.text = data.rating.toString()
            binding.textViewMentor.text = author
            binding.textViewLevelCourse.text = data.level
            binding.progressBar.visibility = View.GONE
            binding.textViewProgressStatus.visibility = View.GONE
            binding.iconProgress.visibility = View.GONE
            if (data.type == "FREE") {
                binding.buttonBuyPopular.text = "Mulai Belajar"
            } else {
                binding.buttonBuyPopular.text = Formatter.formatPrice(data.price)
            }

            if (!data.module.isNullOrEmpty()) {
                binding.textViewTime.text =
                    Formatter.formatTimeSecondToMinute(data.module.sumOf { it.time ?: 0 })
                binding.textViewModule.text = Formatter.formatSizeModule(data.module.size)
            } else {
                binding.textViewModule.text = Formatter.formatSizeModule(0)
                binding.textViewTime.text = Formatter.formatTimeSecondToMinute(0)
            }

            binding.card.setOnClickListener {
                onClick?.invoke(data)
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

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: DataItemCourse, newItem: DataItemCourse
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}