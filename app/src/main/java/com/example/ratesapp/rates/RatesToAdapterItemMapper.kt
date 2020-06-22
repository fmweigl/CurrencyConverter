package com.example.ratesapp.rates

import android.content.res.Resources
import com.example.ratesapp.R
import com.example.ratesapp.domain.model.Rate
import java.math.BigDecimal

class RatesToAdapterItemMapper(
    private val resources: Resources,
    private val numberFormatter: NumberFormatter
) {

    fun map(
        input: String,
        rates: List<Rate>
    ): List<CurrencyAdapterItem> {
        return rates.mapIndexed { index: Int, rate: Rate ->
            val isBaseRate = index == 0
            val inputAsBigDecimal = input.toBigDecimalOrNull()
            val inputValue =
                when {
                    isBaseRate -> input
                    inputAsBigDecimal == null -> ""
                    else -> rateTimesInputToString(rate.value, inputAsBigDecimal)
                }
            CurrencyAdapterItem(
                currencyId = rate.currencyId,
                currencyName = mapCurrencyIdToName(rate.currencyId),
                countryFlagResId = mapCurrencyIdToFlagResourceId(rate.currencyId),
                value = inputValue,
                isBase = isBaseRate
            )
        }

    }

    private fun rateTimesInputToString(rate: BigDecimal, input: BigDecimal): String {
        val value = rate * input
        return numberFormatter.formatBigDecimal(value)
    }

    private fun mapCurrencyIdToName(id: String) = when (id) {
        CurrencyIds.ID_AUD -> resources.getString(R.string.aud)
        CurrencyIds.ID_BGN -> resources.getString(R.string.bgn)
        CurrencyIds.ID_BRL -> resources.getString(R.string.brl)
        CurrencyIds.ID_CAD -> resources.getString(R.string.cad)
        CurrencyIds.ID_CHF -> resources.getString(R.string.chf)
        CurrencyIds.ID_CNY -> resources.getString(R.string.cny)
        CurrencyIds.ID_CZK -> resources.getString(R.string.czk)
        CurrencyIds.ID_DKK -> resources.getString(R.string.dkk)
        CurrencyIds.ID_EUR -> resources.getString(R.string.eur)
        CurrencyIds.ID_GBP -> resources.getString(R.string.gbp)
        CurrencyIds.ID_HKD -> resources.getString(R.string.hkd)
        CurrencyIds.ID_HRK -> resources.getString(R.string.hrk)
        CurrencyIds.ID_HUF -> resources.getString(R.string.huf)
        CurrencyIds.ID_IDR -> resources.getString(R.string.idr)
        CurrencyIds.ID_ILS -> resources.getString(R.string.ils)
        CurrencyIds.ID_INR -> resources.getString(R.string.inr)
        CurrencyIds.ID_ISK -> resources.getString(R.string.isk)
        CurrencyIds.ID_JPY -> resources.getString(R.string.jpy)
        CurrencyIds.ID_KRW -> resources.getString(R.string.krw)
        CurrencyIds.ID_MXN -> resources.getString(R.string.mxn)
        CurrencyIds.ID_MYR -> resources.getString(R.string.myr)
        CurrencyIds.ID_NOK -> resources.getString(R.string.nok)
        CurrencyIds.ID_NZD -> resources.getString(R.string.nzd)
        CurrencyIds.ID_PHP -> resources.getString(R.string.php)
        CurrencyIds.ID_PLN -> resources.getString(R.string.pln)
        CurrencyIds.ID_RON -> resources.getString(R.string.ron)
        CurrencyIds.ID_RUB -> resources.getString(R.string.rub)
        CurrencyIds.ID_SEK -> resources.getString(R.string.sek)
        CurrencyIds.ID_SGD -> resources.getString(R.string.sgd)
        CurrencyIds.ID_THB -> resources.getString(R.string.thb)
        CurrencyIds.ID_USD -> resources.getString(R.string.usd)
        CurrencyIds.ID_ZAR -> resources.getString(R.string.zar)
        else -> null
    }

    private fun mapCurrencyIdToFlagResourceId(id: String) = when (id) {
        CurrencyIds.ID_AUD -> R.drawable.ic_australia
        CurrencyIds.ID_BGN -> R.drawable.ic_bulgaria
        CurrencyIds.ID_BRL -> R.drawable.ic_brazil
        CurrencyIds.ID_CAD -> R.drawable.ic_canada
        CurrencyIds.ID_CHF -> R.drawable.ic_switzerland
        CurrencyIds.ID_CNY -> R.drawable.ic_china
        CurrencyIds.ID_CZK -> R.drawable.ic_czech_republic
        CurrencyIds.ID_DKK -> R.drawable.ic_denmark
        CurrencyIds.ID_EUR -> R.drawable.ic_european_union
        CurrencyIds.ID_GBP -> R.drawable.ic_united_kingdom
        CurrencyIds.ID_HKD -> R.drawable.ic_hong_kong
        CurrencyIds.ID_HRK -> R.drawable.ic_croatia
        CurrencyIds.ID_HUF -> R.drawable.ic_hungary
        CurrencyIds.ID_IDR -> R.drawable.ic_indonesia
        CurrencyIds.ID_ILS -> R.drawable.ic_israel
        CurrencyIds.ID_INR -> R.drawable.ic_india
        CurrencyIds.ID_ISK -> R.drawable.ic_iceland
        CurrencyIds.ID_JPY -> R.drawable.ic_japan
        CurrencyIds.ID_KRW -> R.drawable.ic_south_korea
        CurrencyIds.ID_MXN -> R.drawable.ic_mexico
        CurrencyIds.ID_MYR -> R.drawable.ic_malaysia
        CurrencyIds.ID_NOK -> R.drawable.ic_norway
        CurrencyIds.ID_NZD -> R.drawable.ic_new_zealand
        CurrencyIds.ID_PHP -> R.drawable.ic_philippines
        CurrencyIds.ID_PLN -> R.drawable.ic_republic_of_poland
        CurrencyIds.ID_RON -> R.drawable.ic_romania
        CurrencyIds.ID_RUB -> R.drawable.ic_russia
        CurrencyIds.ID_SEK -> R.drawable.ic_sweden
        CurrencyIds.ID_SGD -> R.drawable.ic_singapore
        CurrencyIds.ID_THB -> R.drawable.ic_thailand
        CurrencyIds.ID_USD -> R.drawable.ic_united_states_of_america
        CurrencyIds.ID_ZAR -> R.drawable.ic_south_africa
        else -> null
    }

    // TODO new base c
    fun moveCurrencyToTopOfAdapterItems(
        selectedCurrencyId: String,
        items: List<CurrencyAdapterItem>
    ): List<CurrencyAdapterItem> {

        val selectedCurrencyItem =
            items.firstOrNull { it.currencyId == selectedCurrencyId } ?: return items
        val sortedOtherItemsSorted = items.minus(selectedCurrencyItem).sortedBy { it.currencyId }
        return listOf(selectedCurrencyItem).plus(sortedOtherItemsSorted)
    }

}