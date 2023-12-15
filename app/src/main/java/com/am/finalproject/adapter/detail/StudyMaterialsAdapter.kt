package com.am.finalproject.adapter.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.multiple_list.materials.DataItemMaterials
import com.am.finalproject.data.multiple_list.materials.DatabaseMaterials
import com.am.finalproject.databinding.ItemHeaderStudyMaterialsBinding
import com.am.finalproject.databinding.ItemStudyMaterialsBinding


class StudyMaterialsAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ItemViewHolder(private val itemBinding: ItemStudyMaterialsBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: DataItemMaterials.Item) {
            itemBinding.textViewNumber.text = "1"
            itemBinding.textViewTitleVideo.text = item.title
        }
    }

    inner class HeadersViewHolder(private val headerBinding: ItemHeaderStudyMaterialsBinding) :
        RecyclerView.ViewHolder(headerBinding.root) {
        fun bind(header: DataItemMaterials.Headers) {
            headerBinding.textViewChapter.text = header.text
        }
    }

    private val itemList = arrayListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            DatabaseMaterials.TYPE_ITEM -> ItemViewHolder(
                ItemStudyMaterialsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            DatabaseMaterials.TYPE_HEADER -> HeadersViewHolder(
                ItemHeaderStudyMaterialsBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            else -> throw IllegalArgumentException("Invalid viewType")
        }
    }

    override fun getItemCount(): Int = itemList.size


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is ItemViewHolder -> holder.bind(itemList[position] as DataItemMaterials.Item)
            is HeadersViewHolder -> holder.bind(itemList[position] as DataItemMaterials.Headers)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is DataItemMaterials.Item -> DatabaseMaterials.TYPE_ITEM
            is DataItemMaterials.Headers -> DatabaseMaterials.TYPE_HEADER
            else -> throw java.lang.IllegalArgumentException("Invalid item")
        }
    }

    fun updateList(updatedList: List<Any>) {
        itemList.clear()
        itemList.addAll(updatedList)
        notifyDataSetChanged()
    }
}