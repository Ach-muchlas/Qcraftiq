package com.am.finalproject.adapter.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.remote.DataItemCategory
import com.am.finalproject.databinding.ItemCategoryBinding
import com.bumptech.glide.Glide

class HomeCategoryAdapter :
    ListAdapter<DataItemCategory, HomeCategoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    inner class MyViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemCategory) {
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

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemCategory>() {
            override fun areItemsTheSame(
                oldItem: DataItemCategory,
                newItem: DataItemCategory
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemCategory,
                newItem: DataItemCategory
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}