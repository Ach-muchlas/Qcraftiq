package com.am.finalproject.adapter.home

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.local.entity.CourseEntity
import com.am.finalproject.data.remote.DataItemHistory
import com.am.finalproject.databinding.ItemPopularCourseBinding
import com.am.finalproject.utils.Formatter
import com.bumptech.glide.Glide

class HomePopularCourseAdapter :
    ListAdapter<CourseEntity, HomePopularCourseAdapter.MyViewHolder>(DIFF_CALLBACK) {

    var onClick: ((CourseEntity) -> Unit)? = null
    var textButton: ((DataItemHistory) -> Unit)? = null

    inner class MyViewHolder(private val binding: ItemPopularCourseBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bindContentPopularCourse(data: CourseEntity) {
            val author = "By " + data.authorBy
            binding.textViewTagLineCategory.text = data.categoryTitle
            binding.textViewTitleCourse.text = data.title
            Glide.with(binding.root.context).load(data.image).into(binding.imageContent)
            binding.textViewRating.text = data.rating.toString()
            binding.textViewMentor.text = author
            binding.textViewLevelCourse.text = data.level
            binding.progressBar.visibility = View.GONE
            binding.textViewProgressStatus.visibility = View.GONE
            binding.iconProgress.visibility = View.GONE
            textButton = {
                Log.e("SIMPLE", "data : ${it.course?.title}")
                when (it.status) {
                    "WAITING", "APPROVED" -> {
                        Log.e("SIMPLE2", "data : ${it.course?.title}")
                        binding.buttonBuyPopular.text = "MUlai Belajar"
                    }
                    else -> {
                        Log.e("SIMPLE3", "data : ${it.course?.title}")
                        binding.buttonBuyPopular.text = Formatter.formatPrice(data.price)
                    }
                }
            }
            binding.card.setOnClickListener {
                onClick?.invoke(data)
            }
            binding.buttonBuyPopular.text = Formatter.formatPrice(data.price)
            binding.textViewModule.text = Formatter.formatSizeModule(data.module)
            binding.textViewTime.text = Formatter.formatTimeSecondToMinute(data.time)
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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CourseEntity>() {
            override fun areItemsTheSame(
                oldItem: CourseEntity,
                newItem: CourseEntity
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: CourseEntity, newItem: CourseEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}