package com.am.finalproject.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

object Formatter {

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatLocalDate(localDate: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("d MMMM, HH:mm", Locale("id-ID"))
        return localDate.format(formatter)
    }
}