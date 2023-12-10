package com.am.finalproject.adapter.home

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.local.entity.CategoryEntity
import com.am.finalproject.databinding.ItemCategoryBinding
import com.bumptech.glide.Glide
import kotlin.math.min

class HomeCategoryAdapter :
    ListAdapter<CategoryEntity, HomeCategoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    var showAllItems: Boolean = false

    inner class MyViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: CategoryEntity) {
            binding.textViewTitle.text = data.title
            Glide.with(binding.root.context).load(data.image).into(binding.imageContent)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding =
            ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val dataCategory = getItem(position)
        holder.bind(dataCategory)
    }

    override fun getItemCount(): Int {
        return if (showAllItems) {
            super.getItemCount()
        } else {
            min(super.getItemCount(), 4)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CategoryEntity>() {
            override fun areItemsTheSame(
                oldItem: CategoryEntity,
                newItem: CategoryEntity
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: CategoryEntity,
                newItem: CategoryEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}