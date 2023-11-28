package com.am.finalproject.utils

import org.junit.Assert
import org.junit.Test

class FormatterTest {
    @Test
    fun `given correct ISO 8601 format then should format correctly`() {
        val currentDate = "2022-02-20T10:10:10Z"
        Assert.assertEquals("20 Feb 2022 | 17:10", Formatter.formatLocalDate(currentDate, "Asia/Jakarta"))
        Assert.assertEquals("20 Feb 2022 | 18:10", Formatter.formatLocalDate(currentDate, "Asia/Makassar"))
        Assert.assertEquals("20 Feb 2022 | 19:10", Formatter.formatLocalDate(currentDate, "Asia/Jayapura"))
    }
}