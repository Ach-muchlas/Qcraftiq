package com.am.finalproject.utils

import android.widget.EditText
import java.net.URLEncoder
import java.text.DecimalFormat
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

object Formatter {
    fun formatDate(currentDate: String, targetTimeZone: String): String {
        val instant = Instant.parse(currentDate)
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy | HH:mm")
            .withZone(ZoneId.of(targetTimeZone))
        return formatter.format(instant)
    }

    fun formatCurrency(amount: Int): String {
        val decimal = DecimalFormat("#,###.##")
        return "Beli    Rp. " + decimal.format(amount)
    }

    fun formatTimeSecondToMinute(time: Int): String {
        val minutes = time / 60
        return "$minutes Menit"
    }

    fun formatSizeModule(module: Int): String {
        return "$module Modul"
    }

    fun formatPPN(price: Int): String {
        val decimal = DecimalFormat("#,###")
        return "Rp ${decimal.format(price)}"
    }

    fun formatTotalPayment(price: Int, ppn: Int): String {
        val decimal = DecimalFormat("#,###.##")
        val total = price.plus(ppn)
        return "Rp ${decimal.format(total)}"
    }

    fun formatPrice(price: Int): String {
        val decimal = DecimalFormat("#,###.##")
        return "Rp ${decimal.format(price)}"
    }

    fun formatDate(editText: EditText, calendar: Calendar) {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        editText.setText(dateFormat.format(calendar.time))
    }

    fun String.urlEncode(): String = URLEncoder.encode(this, "UTF-8")

    fun extractYouTubeId(youTubeUrl: String): String? {
        var videoId:String? = null
        val pattern = Regex("^https?://.*(?:youtu.be/|v/|u/\\w/|embed/|watch?v=)([^#&?]*).*$", RegexOption.IGNORE_CASE)
        val matcher = pattern.find(youTubeUrl)
        if (matcher != null && matcher.groupValues.size > 1) {
            videoId = matcher.groupValues[1]
        }
        return videoId
    }

}