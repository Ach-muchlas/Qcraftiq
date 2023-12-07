package com.am.finalproject.utils

import java.text.NumberFormat
import java.util.Locale

object Formatter {
//    fun formatLocalDate(currentDate: String, targetTimeZone: String): String {
//        val instant = Instant.parse(currentDate)
//        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm")
//            .withZone(ZoneId.of(targetTimeZone))
//        return formatter.format(instant)
//    }

    fun formatterRupiah(amount: Int): String {
        val formatter: NumberFormat = NumberFormat.getCurrencyInstance(Locale("id", "ID"))
        return formatter.format(amount.toLong())
    }
}