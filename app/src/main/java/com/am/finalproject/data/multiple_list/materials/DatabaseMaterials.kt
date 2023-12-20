package com.am.finalproject.data.multiple_list.materials

import com.am.finalproject.data.remote.DataItemCourse

object DatabaseMaterials {
    const val TYPE_HEADER = 0
    const val TYPE_ITEM = 1

    fun getItem(data: List<DataItemCourse>): ArrayList<Any> {
        val items = arrayListOf<Any>()

        items.add(DataItemMaterials.Headers("Chapter 1 - Pendahuluan"))
        var numberForChapterOne = 1
        for (course in data) {
            course.module?.let { modules ->
                for (item in modules) {
                    if (item.chapter == 1) {
                        items.add(
                            DataItemMaterials.Item(
                                numberForChapterOne++,
                                item.title.toString()
                            )
                        )
                    }
                }
            }
        }

        items.add(DataItemMaterials.Headers("Chapter 2 - Mulai Materi"))
        var numberForChapterTwo = 1
        for (course in data) {
            course.module?.let { modules ->
                for (item in modules) {
                    if (item.chapter == 2) {
                        items.add(
                            DataItemMaterials.Item(
                                numberForChapterTwo++,
                                item.title.toString()
                            )
                        )
                    }
                }
            }
        }

        return items
    }
}

