package com.calcar.common.ui.utils

import java.text.DecimalFormat
import java.util.Currency

fun Double.toEuroCurrency(): String {
    val decimalFormat = DecimalFormat.getCurrencyInstance().apply {
        currency = Currency.getInstance("EUR")
    }
    return decimalFormat.format(this)
}
