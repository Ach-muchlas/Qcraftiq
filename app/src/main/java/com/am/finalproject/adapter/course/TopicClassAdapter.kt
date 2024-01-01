package com.am.finalproject.adapter.course

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.R
import com.am.finalproject.data.local.entity.CourseEntity
import com.am.finalproject.databinding.ItemClassCourseBinding
import com.am.finalproject.utils.Formatter
import com.bumptech.glide.Glide

class TopicClassAdapter :
    ListAdapter<CourseEntity, TopicClassAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onClick: ((CourseEntity) -> Unit)? = null

    inner class MyViewHolder(private val binding: ItemClassCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CourseEntity) {
            val author = "By " + data.authorBy
            Glide.with(binding.root.context).load(data.image).into(binding.imageContent)
            binding.textViewTagLineCategory.text = data.categoryTitle
            binding.textViewTitleCourse.text = data.title
            binding.textViewRate.text = data.rating.toString()
            binding.textViewMentor.text = author
            binding.textViewLevelCourse.text = data.level
            binding.textViewModule.text = Formatter.formatSizeModule(data.module)
            binding.textViewTime.text = Formatter.formatTimeSecondToMinute(data.time)
            binding.cardTopicClass.setOnClickListener {
                onClick?.invoke(data)
            }
            if (data.type == "FREE") {
                binding.textViewContentCard.text =
                    binding.root.context.getString(R.string.mulai_kelas)
                binding.iconContentCard.visibility = View.GONE
            } else {
                binding.textViewContentCard.text = data.type
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemClassCourseBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataTopicClass = getItem(position)
        holder.bind(dataTopicClass)
    }


    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CourseEntity>() {
            override fun areItemsTheSame(
                oldItem: CourseEntity,
                newItem: CourseEntity
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: CourseEntity,
                newItem: CourseEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}