package com.am.finalproject.data

import com.am.finalproject.data.remote.CategoryResponse

object Database {
    const val TYPE_HEADER = 0
    const val TYPE_ITEM = 1

    fun getItem(categoryResponse: CategoryResponse): ArrayList<Any> {
        val items = arrayListOf<Any>()

        items.add(DataItem.Headers("Category"))
        for (category in categoryResponse.data) {
            items.add(DataItem.Item(category.title))
        }

        items.add(DataItem.Headers("Level"))
        items.add(DataItem.Item("Beginner"))
        items.add(DataItem.Item("Advance"))

        return items
    }
}

