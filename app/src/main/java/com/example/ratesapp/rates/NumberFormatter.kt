package com.example.ratesapp.rates

import java.math.BigDecimal
import java.math.RoundingMode

private const val PERIOD = '.'
private const val ZERO = '0'

class NumberFormatter {

    fun formatBigDecimal(bigDecimal: BigDecimal): String {
        val string = bigDecimal.setScale(2, RoundingMode.HALF_UP)
            .stripTrailingZeros()
            .toPlainString()

        if (string.contains(PERIOD) && string.substringAfter(PERIOD).length == 1) {
            return string.plus(ZERO)
        }

        return string
    }

}