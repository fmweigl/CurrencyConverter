package com.example.currencyconverter.rates

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import java.math.BigDecimal

class NumberFormatterTest {

    private lateinit var tested: NumberFormatter

    @Before
    fun setUp() {
        tested = NumberFormatter()
    }

    @Test
    fun `should format integer without decimal places`() {
        assertEquals("3", tested.formatBigDecimal(BigDecimal.valueOf(3)))
        assertEquals("3", tested.formatBigDecimal(BigDecimal.valueOf(3.0)))
    }

    @Test
    fun `should round to 2 decimal places`() {
        assertEquals("1.23", tested.formatBigDecimal(BigDecimal.valueOf(1.2299999)))
    }

    @Test
    fun `should format so there is 2 decimal places even when 2nd decimal place is zero`() {
        assertEquals("1.20", tested.formatBigDecimal(BigDecimal.valueOf(1.2)))
    }

}