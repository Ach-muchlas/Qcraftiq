package com.am.finalproject.data

import com.am.finalproject.data.remote.CategoryResponse

val dataDummyNotification = listOf(
    Notification(
        tagLine = "Notifikasi",
        title = "Password Diganti",
        time = "2022-02-20T10:10:10Z",
        description = null
    ),
    Notification(
        tagLine = "Promosi",
        title = "Dapatkan Diskon 50% selama Bulan ini",
        time = "2022-02-20T10:10:10Z",
        description = "Syarat dan ketentuan berlaku"
    )
)

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

