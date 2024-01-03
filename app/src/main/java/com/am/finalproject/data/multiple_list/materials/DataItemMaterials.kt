package com.am.finalproject.data.multiple_list.materials

sealed class DataItemMaterials{
    data class Item(val no : Int, val title : String, val urlYoutube : String, var isClicked : Boolean) : DataItemMaterials()
    data class Headers(val text : String, val amount : String) : DataItemMaterials()
}
