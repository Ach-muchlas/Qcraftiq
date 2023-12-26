package com.am.finalproject.data.multiple_list.filter

sealed class DataItemFilter{
    data class Item(val id: String, val type: String, val title: String, var isChecked: Boolean) :
        DataItemFilter()

    data class Headers(val text: String) : DataItemFilter()
}
