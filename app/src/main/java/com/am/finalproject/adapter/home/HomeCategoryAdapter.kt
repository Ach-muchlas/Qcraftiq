package com.am.finalproject.adapter.home

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.R
import com.am.finalproject.data.local.entity.CategoryEntity
import com.am.finalproject.data.remote.DataItemCategory
import com.am.finalproject.databinding.ItemCategoryBinding
import com.am.finalproject.ui.home.HomeFragment
import com.am.finalproject.ui.home.HomeFragmentDirections
import com.bumptech.glide.Glide
import kotlin.math.min

class HomeCategoryAdapter :
    ListAdapter<DataItemCategory, HomeCategoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    var showAllItems: Boolean = false
    var callBackSearchByIdCategory : ((String) -> Unit)? = null
    inner class MyViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(data: DataItemCategory) {
            binding.textViewTitle.text = data.title
            Glide.with(binding.root.context).load(data.image).into(binding.imageContent)
            binding.itemViewCategory.setOnClickListener {
                callBackSearchByIdCategory?.invoke(data.title)
            }

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
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemCategory>() {
            override fun areItemsTheSame(
                oldItem: DataItemCategory,
                newItem: DataItemCategory
            ): Boolean {
                return oldItem == newItem
            }

            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(
                oldItem: DataItemCategory,
                newItem: DataItemCategory
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}