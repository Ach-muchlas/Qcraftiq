package com.am.finalproject.utils

import java.text.DecimalFormat
import java.text.NumberFormat
import java.util.Locale

object Formatter {
//    fun formatLocalDate(currentDate: String, targetTimeZone: String): String {
//        val instant = Instant.parse(currentDate)
//        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm")
//            .withZone(ZoneId.of(targetTimeZone))
//        return formatter.format(instant)
//    }

    fun formatCurrency(amount: Int): String {
        val decimal = DecimalFormat("#,###.##")
        return "Rp. " + decimal.format(amount)
    }
}