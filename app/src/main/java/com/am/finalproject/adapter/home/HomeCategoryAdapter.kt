package com.am.finalproject.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.Category
import com.am.finalproject.databinding.ItemCategoryBinding

class HomeCategoryAdapter : ListAdapter<Category, HomeCategoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    inner class MyViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: Category) {
            binding.textViewTitle.text = data.name
            binding.imageContent.setImageResource(data.imageUrl)
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

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(
                oldItem: Category,
                newItem: Category
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: Category,
                newItem: Category
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}