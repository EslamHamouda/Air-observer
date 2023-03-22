package com.example.airobserver.utils

import java.text.SimpleDateFormat
import java.util.*

fun dateFormat(itemDate: String?): String {
    val formatterIn = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ROOT)
    val formatterOut = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ROOT)
    val date = itemDate?.let { formatterIn.parse(it) } as Date
    return formatterOut.format(date)
}