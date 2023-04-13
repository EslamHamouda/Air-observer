package com.ekndev.gaugelibrary

import com.ekndev.gaugelibrary.contract.ValueFormatter

class ValueFormatterImpl : ValueFormatter {
    override fun getFormattedValue(value: Int): String {
        return value.toString()
    }
}