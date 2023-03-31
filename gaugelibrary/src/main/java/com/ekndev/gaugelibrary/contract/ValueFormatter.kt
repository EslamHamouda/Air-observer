package com.ekndev.gaugelibrary.contract

interface ValueFormatter {
    fun getFormattedValue(value: Double): String?
}