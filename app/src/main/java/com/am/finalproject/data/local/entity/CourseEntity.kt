package com.am.finalproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "course")
class CourseEntity(
    @field:ColumnInfo(name = "title")
    @field:PrimaryKey
    val title: String,

    @field:ColumnInfo("id")
    val id : String,

    @field:ColumnInfo("image")
    val image: String,

    @field:ColumnInfo("level")
    val level: String,

    @field:ColumnInfo("authorBy")
    val authorBy: String,

    @field:ColumnInfo("rating")
    val rating: Double,

    @field:ColumnInfo("price")
    val price: Int,

    @field:ColumnInfo("categoryTitle")
    val categoryTitle: String,

    @field:ColumnInfo("time")
    val time: Int,

    @field:ColumnInfo("module")
    val module: Int,
    )
