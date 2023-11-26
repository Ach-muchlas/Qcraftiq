package com.am.finalproject.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.am.finalproject.R
import java.time.LocalDate

val dataDummyCategory: List<Category> = listOf(
    Category(
        imageUrl = R.drawable.foto,
        name = "Web Development"
    ),
    Category(
        imageUrl = R.drawable.foto,
        name = "Mobile Development"
    ),
    Category(
        imageUrl = R.drawable.foto,
        name = "UI/UX Development"
    ),
    Category(
        imageUrl = R.drawable.foto,
        name = "Product Management"
    )
)


val dataDummyCourse: List<Course> = listOf(
    Course(
        imageUrl = R.drawable.foto,
        tagLine = "Mobile Development",
        title = "Belajar Dasar Pemograman Mobile",
        mentor = "Achmad Muchlasin",
        level = "Advance level",
        module = "19 Modul",
        time = "60 Menit",
        rate = 5.0,
        price = "Rp 100.000"
    ),

    Course(
        imageUrl = R.drawable.foto,
        tagLine = "Mobile Development",
        title = "Belajar Dasar Pemograman Mobile",
        mentor = "Achmad Muchlasin",
        level = "Advance level",
        module = "10 Modul",
        time = "60 Menit",
        rate = 4.7,
        price = "Rp 250.000"
    )
)

val dataDummyNotification = listOf(
    Notification(
        tagLine = "Notifikasi",
        title = "Password Diganti",
        time = "22 Maret, 10:00",
        description = null
    ),
    Notification(
        tagLine = "Promosi",
        title = "Dapatkan Diskon 50% selama Bulan ini",
        time = "06 September, 00:00",
        description = "Syarat dan ketentuan berlaku"
    )
)