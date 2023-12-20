package com.am.finalproject.data

sealed class DataItem{
    data class Item(val title : String) : DataItem()
    data class Headers(val text : String) : DataItem()
}
