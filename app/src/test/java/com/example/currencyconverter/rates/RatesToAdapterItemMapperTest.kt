package com.example.currencyconverter.rates

import android.content.res.Resources
import com.example.currencyconverter.domain.model.Rate
import com.nhaarman.mockitokotlin2.given
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.math.BigDecimal

class RatesToAdapterItemMapperTest {

    @Mock
    private lateinit var resources: Resources

    @Mock
    private lateinit var numberFormatter: NumberFormatter

    @InjectMocks
    private lateinit var tested: RatesToAdapterItemMapper

    @Before
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun `should mark first rate as base`() {
        val rates = listOf(
            Rate("EUR", BigDecimal.valueOf(1)),
            Rate("USD", BigDecimal.valueOf(2)),
            Rate("CHF", BigDecimal.valueOf(3))
        )

        val items = tested.map("", rates)

        assertTrue(items[0].isBase)
        assertFalse(items[1].isBase)
        assertFalse(items[2].isBase)
    }

    @Test
    fun `should set input as value for base rate`() {
        val input = "3."

        val item = tested.map(input, listOf(Rate("EUR", BigDecimal.valueOf(1)))).first()

        assertEquals(input, item.displayValue)
    }

    @Test
    fun `should set value to empty string for non-base rate if input is not a number`() {
        val input = "xy"

        val item = tested.map(
            input,
            listOf(
                Rate("EUR", BigDecimal.valueOf(1)),
                Rate("USD", BigDecimal.valueOf(2))
            )
        )[1]

        assertEquals("", item.displayValue)
    }

    @Test
    fun `should set value to formatted product of input and rate for non-base rate`() {
        val input = "3"
        val exchangeValue = BigDecimal.valueOf(2)
        val formattedDisplayValue = "6.00"
        val inputAndExchangeValueProduct = BigDecimal.valueOf(6) // 3 * 2
        given(numberFormatter.formatBigDecimal(inputAndExchangeValueProduct))
            .willReturn(formattedDisplayValue)
        val rate = Rate("USD", exchangeValue)

        val item = tested.map(
            input,
            listOf(
                Rate("EUR", BigDecimal.valueOf(1)),
                rate
            )
        )[1]

        assertEquals(formattedDisplayValue, item.displayValue)
    }

    @Test
    fun `should map currency id`() {
        val currencyId = "currencyId"

        val item = tested.map("", listOf(Rate(currencyId, BigDecimal.valueOf(1)))).first()

        assertEquals(currencyId, item.currencyId)
    }

    @Test
    fun `should reorder selected currency item to top and sort rest alphabetic`() {
        val itemA = CurrencyAdapterItem("A", null, null, "", false)
        val itemB = CurrencyAdapterItem("B", null, null, "", false)
        val itemC = CurrencyAdapterItem("C", null, null, "", false)
        val itemD = CurrencyAdapterItem("D", null, null, "", false)
        val itemE = CurrencyAdapterItem("E", null, null, "", false)
        val items = listOf(itemB, itemA, itemE, itemD, itemC)

        val resultItems = tested.moveCurrencyToTopOfAdapterItems("C", items)
        val expectedResultItems = listOf(itemC, itemA, itemB, itemD, itemE)
        assertEquals(expectedResultItems, resultItems)
    }
}