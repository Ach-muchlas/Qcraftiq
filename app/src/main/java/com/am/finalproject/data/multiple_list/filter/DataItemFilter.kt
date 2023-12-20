package com.am.finalproject.data.multiple_list.filter

sealed class DataItemFilter{
    data class Item(val title : String) : DataItemFilter()
    data class Headers(val text : String) : DataItemFilter()
}
