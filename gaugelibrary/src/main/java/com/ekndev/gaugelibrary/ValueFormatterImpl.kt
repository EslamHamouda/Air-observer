package com.ekndev.gaugelibrary

import com.ekndev.gaugelibrary.contract.ValueFormatter

class ValueFormatterImpl : ValueFormatter {
    override fun getFormattedValue(value: Double): String {
        return value.toString()
    }
}