package com.am.finalproject.data.multiple_list.materials

sealed class DataItemMaterials{
    data class Item(val no : Int, val title : String) : DataItemMaterials()
    data class Headers(val text : String) : DataItemMaterials()
}
