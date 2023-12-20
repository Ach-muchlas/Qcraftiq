package com.am.finalproject.data

import java.time.LocalDate

data class Notification(
    val tagLine: String,
    val title : String,
    val description : String? = null,
    val time : String
)
