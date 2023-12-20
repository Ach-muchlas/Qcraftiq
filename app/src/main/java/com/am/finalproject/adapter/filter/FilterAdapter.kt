package com.am.finalproject.adapter.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.am.finalproject.data.multiple_list.filter.DataItemFilter
import com.am.finalproject.data.multiple_list.filter.DatabaseFilter.TYPE_HEADER
import com.am.finalproject.data.multiple_list.filter.DatabaseFilter.TYPE_ITEM
import com.am.finalproject.databinding.ItemFilterBinding
import com.am.finalproject.databinding.ItemHeadersBinding

class FilterAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    inner class ItemViewHolder(private val itemBinding: ItemFilterBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bind(item: DataItemFilter.Item) {
            itemBinding.radioButtonUiUx.text = item.title
        }
    }

    inner class HeadersViewHolder(private val headerBinding: ItemHeadersBinding) :
        RecyclerView.ViewHolder(headerBinding.root) {
        fun bind(header: DataItemFilter.Headers) {
            headerBinding.textViewTitleFilterCategory.text = header.text
        }
    }

    private val itemList = arrayListOf<Any>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_ITEM -> ItemViewHolder(
                ItemFilterBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )

            TYPE_HEADER -> HeadersViewHolder(
                ItemHeadersBinding.inflate(
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
            is ItemViewHolder -> holder.bind(itemList[position] as DataItemFilter.Item)
            is HeadersViewHolder -> holder.bind(itemList[position] as DataItemFilter.Headers)
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(itemList[position]){
            is DataItemFilter.Item -> TYPE_ITEM
            is DataItemFilter.Headers -> TYPE_HEADER
            else -> throw java.lang.IllegalArgumentException("Invalid item")
        }
    }

    fun updateList(updatedList : List<Any>){
        itemList.clear()
        itemList.addAll(updatedList)
        notifyDataSetChanged()
    }



}