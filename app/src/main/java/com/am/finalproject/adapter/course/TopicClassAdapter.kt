package com.am.finalproject.adapter.course

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.R
import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.databinding.ItemClassCourseBinding
import com.am.finalproject.ui.bottom_sheet.OrdersBottomSheetFragment
import com.am.finalproject.ui.details.DetailsActivity
import com.am.finalproject.utils.Formatter
import com.bumptech.glide.Glide

class TopicClassAdapter(private val fragmentManager: FragmentManager) :
    ListAdapter<DataItemCourse, TopicClassAdapter.MyViewHolder>(DIFF_CALLBACK) {
    inner class MyViewHolder(private val binding: ItemClassCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemCourse) {
            Glide.with(binding.root.context).load(data.image).into(binding.imageContent)
            binding.textViewTagLineCategory.text = data.category.title
            binding.textViewTitleCourse.text = data.title
            binding.textViewRate.text = data.rating.toString()
            binding.textViewMentor.text = data.authorBy
            binding.textViewLevelCourse.text = data.level

            if (!data.module.isNullOrEmpty()) {
                binding.textViewTime.text =
                    Formatter.formatTimeSecondToMinute(data.module.sumOf { it.time ?: 0 })
                binding.textViewModule.text = Formatter.formatSizeModule(data.module.size)
            } else {
                binding.textViewModule.text = Formatter.formatSizeModule(0)
                binding.textViewTime.text = Formatter.formatTimeSecondToMinute(0)
            }

            if (data.type == "FREE") {
                binding.textViewContentCard.text =
                    binding.root.context.getString(R.string.mulai_kelas)
                binding.iconContentCard.visibility = View.GONE
                binding.cardTopicClass.setOnClickListener {
                    val bundle = Bundle().apply {
                        putString(DetailsActivity.KEY_ID, data.id)
                    }
                    val intent = Intent(itemView.context, DetailsActivity::class.java).apply {
                        putExtras(bundle)
                    }
                    itemView.context.startActivity(intent)
                }
            } else {
                binding.textViewContentCard.text = data.type
                binding.cardTopicClass.setOnClickListener {
                    OrdersBottomSheetFragment.show(fragmentManager, data.id)
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
        val dataTopicClass = getItem(position)
        holder.bind(dataTopicClass)
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