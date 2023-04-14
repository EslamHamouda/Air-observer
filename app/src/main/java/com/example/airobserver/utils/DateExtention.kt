package com.example.airobserver.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter
import java.util.*

fun dateFormat(itemDate: String?): String {
    val formatterIn = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.ROOT)
    val formatterOut = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.ROOT)
    val date = itemDate?.let { formatterIn.parse(it) } as Date
    return formatterOut.format(date)
}

@RequiresApi(Build.VERSION_CODES.O)
fun convertTo12HourAndDateFormat(dateString: String?): String {
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'")
    val dateTime = LocalDateTime.parse(dateString, formatter)
    val hour = dateTime.hour
    val convertedHour = if (hour == 0) 12 else if (hour > 12) hour - 12 else hour
    val minute = dateTime.minute
    val suffix = if (hour < 12) "AM" else "PM"
    return String.format("%s %d:%02d %s", dateTime.toLocalDate(), convertedHour, minute, suffix)
}

fun convertTo12HourFormat(hour: Int): String {
    var convertedHour = hour % 12
    if (convertedHour == 0) {
        convertedHour = 12
    }
    val suffix = if (hour < 12) "AM" else "PM"
    return "$convertedHour$suffix"
}

@RequiresApi(Build.VERSION_CODES.O)
fun getMonthName(monthNumber: Int): String {
    return Month.of(monthNumber).name.lowercase().replaceFirstChar { it.uppercase() }
}
