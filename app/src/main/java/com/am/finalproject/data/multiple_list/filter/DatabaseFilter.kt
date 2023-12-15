package com.am.finalproject.data.multiple_list.filter

import com.am.finalproject.data.remote.CategoryResponse

object DatabaseFilter {
    const val TYPE_HEADER = 0
    const val TYPE_ITEM = 1

    fun getItem(categoryResponse: CategoryResponse): ArrayList<Any> {
        val items = arrayListOf<Any>()

        items.add(DataItemFilter.Headers("Category"))
        for (category in categoryResponse.data) {
            items.add(DataItemFilter.Item(category.title))
        }

        items.add(DataItemFilter.Headers("Level"))
        items.add(DataItemFilter.Item("Beginner"))
        items.add(DataItemFilter.Item("Advance"))

        return items
    }
}

