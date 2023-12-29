package com.am.finalproject.data.multiple_list.materials

import com.am.finalproject.data.remote.DataItemCourse
import com.am.finalproject.utils.Formatter

object DatabaseMaterials {
    const val TYPE_HEADER = 0
    const val TYPE_ITEM = 1

    fun getItem(data: List<DataItemCourse>): ArrayList<Any> {
        val items = arrayListOf<Any>()

        val chapterOneTime = data
            .flatMap { it.module ?: emptyList() }
            .filter { it.chapter == 1 }
            .sumBy { it.time ?: 0 }

        val chapterTwoTime = data
            .flatMap { it.module ?: emptyList() }
            .filter { it.chapter == 2 }
            .sumBy { it.time ?: 0 }

        items.add(
            DataItemMaterials.Headers(
                "Chapter 1 - Pendahuluan",
                Formatter.formatTimeSecondToMinute(chapterOneTime)
            )
        )
        var numberForChapterOne = 1
        for (course in data) {
            course.module?.let { modules ->
                for (item in modules) {
                    if (item.chapter == 1) {
                        items.add(
                            DataItemMaterials.Item(
                                numberForChapterOne++,
                                item.title.toString(),
                                item.video.toString()
                            )
                        )
                    }
                }
            }
        }

        items.add(
            DataItemMaterials.Headers(
                "Chapter 2 - Mulai Materi",
                Formatter.formatTimeSecondToMinute(chapterTwoTime)
            )
        )
        var numberForChapterTwo = 1
        for (course in data) {
            course.module?.let { modules ->
                for (item in modules) {
                    if (item.chapter == 2) {
                        items.add(
                            DataItemMaterials.Item(
                                numberForChapterTwo++,
                                item.title.toString(),
                                item.video.toString()
                            )
                        )
                    }
                }
            }
        }

        return items
    }
}

