package com.am.finalproject.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "module")
class ModuleEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: String,

    @field:ColumnInfo(name = "video")
    val video: String,

    @field:ColumnInfo(name = "time")
    val time: Int,

    @field:ColumnInfo(name = "title")
    val title: String,
)