package com.am.finalproject.utils

import org.junit.Assert
import org.junit.Assert.*
import org.junit.Test

class DisplayLayoutTest{

    @Test
    fun testPhoneNumberIsValid(){
        val phone = "1232932347921"
        Assert.assertEquals(true, DisplayLayout.isValidPhoneNumber(phone))
    }
}