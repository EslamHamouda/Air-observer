package com.example.airobserver.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat

fun isValidPhoneNumber(phoneNumber: String): Boolean {
    val pattern = Regex("^(01)[0-9]{9}$")
    return pattern.matches(phoneNumber)
}

@SuppressLint("SimpleDateFormat")
fun isValidDate(date: String): Boolean {
    val regex = "^\\d{4}-\\d{2}-\\d{2}$"
    if (!date.matches(regex.toRegex())) {
        return false
    }
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    sdf.isLenient = false
    try {
        sdf.parse(date)
    } catch (e: ParseException) {
        return false
    }
    return true
}

fun isValidEmail(email: String): Boolean {
    val regex = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}\$"
    return email.matches(regex.toRegex())
}