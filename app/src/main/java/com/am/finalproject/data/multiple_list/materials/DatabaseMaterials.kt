package com.am.finalproject.data.multiple_list.materials

import com.am.finalproject.data.remote.DataItemCourse

object DatabaseMaterials {
    const val TYPE_HEADER = 0
    const val TYPE_ITEM = 1

    fun getItem(data: List<DataItemCourse>): ArrayList<Any> {
        val items = arrayListOf<Any>()

        items.add(DataItemMaterials.Headers("Chapter 1 - Pendahuluan"))
        for (course in data) {
            course.module?.let { modules ->
                for (data in modules) {
                    if (data.chapter == 1) {
                        items.add(DataItemMaterials.Item(data.chapter, data.title.toString()))
                    }
                }
            }
        }

        items.add(DataItemMaterials.Headers("Chapter 2 - Mulai Materi"))
        for (course in data) {
            course.module?.let { modules ->
                for (data in modules) {
                    if (data.chapter == 2) {
                        items.add(DataItemMaterials.Item(data.chapter, data.title.toString()))
                    }
                }
            }
        }

        return items
    }
}

