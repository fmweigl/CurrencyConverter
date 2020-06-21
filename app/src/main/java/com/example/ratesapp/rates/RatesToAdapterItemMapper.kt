package com.example.ratesapp.rates

import android.content.res.Resources
import com.example.ratesapp.R
import com.example.ratesapp.domain.model.Rates

private const val ID_AUD = "aud"
private const val ID_BGN = "bgn"
private const val ID_BRL = "brl"
private const val ID_CAD = "cad"
private const val ID_CHF = "chf"
private const val ID_CNY = "cny"
private const val ID_CZK = "czk"
private const val ID_DKK = "dkk"
private const val ID_EUR = "eur"
private const val ID_GBP = "gbp"
private const val ID_HKD = "hkd"
private const val ID_HRK = "hrk"
private const val ID_HUF = "huf"
private const val ID_IDR = "idr"
private const val ID_ILS = "ils"
private const val ID_INR = "inr"
private const val ID_ISK = "isk"
private const val ID_JPY = "jpy"
private const val ID_KRW = "krw"
private const val ID_MXN = "mxn"
private const val ID_MYR = "myr"
private const val ID_NOK = "nok"
private const val ID_NZD = "nzd"
private const val ID_PHP = "php"
private const val ID_PLN = "pln"
private const val ID_RON = "ron"
private const val ID_RUB = "rub"
private const val ID_SEK = "sek"
private const val ID_SGD = "sgd"
private const val ID_THB = "thb"
private const val ID_USD = "usd"
private const val ID_ZAR = "zar"

class RatesToAdapterItemMapper(private val resources: Resources) {

    fun map(rates: Rates): List<CurrencyAdapterItem> {
        val baseCurrencyItem =
            CurrencyAdapterItem(
                currencyId = rates.baseCurrency,
                currencyName = mapCurrencyIdToName(rates.baseCurrency),
                currencyFlagResId = mapCurrencyIdToFlagResourceId(rates.baseCurrency),
                isBase = true,
                value = 1f
            )
        val otherCurrencyAdapterItems = rates.currencyRates.map {
            CurrencyAdapterItem(
                currencyId = it.first,
                currencyName = mapCurrencyIdToName(it.first),
                currencyFlagResId = mapCurrencyIdToFlagResourceId(it.first),
                isBase = false,
                value = it.second
            )
        }

        return listOf(baseCurrencyItem).plus(otherCurrencyAdapterItems)
    }

    private fun mapCurrencyIdToName(id: String) = when (id.toLowerCase()) {
        ID_AUD -> resources.getString(R.string.aud)
        ID_BGN -> resources.getString(R.string.bgn)
        ID_BRL -> resources.getString(R.string.brl)
        ID_CAD -> resources.getString(R.string.cad)
        ID_CHF -> resources.getString(R.string.chf)
        ID_CNY -> resources.getString(R.string.cny)
        ID_CZK -> resources.getString(R.string.czk)
        ID_DKK -> resources.getString(R.string.dkk)
        ID_EUR -> resources.getString(R.string.eur)
        ID_GBP -> resources.getString(R.string.gbp)
        ID_HKD -> resources.getString(R.string.hkd)
        ID_HRK -> resources.getString(R.string.hrk)
        ID_HUF -> resources.getString(R.string.huf)
        ID_IDR -> resources.getString(R.string.idr)
        ID_ILS -> resources.getString(R.string.ils)
        ID_INR -> resources.getString(R.string.inr)
        ID_ISK -> resources.getString(R.string.isk)
        ID_JPY -> resources.getString(R.string.jpy)
        ID_KRW -> resources.getString(R.string.krw)
        ID_MXN -> resources.getString(R.string.mxn)
        ID_MYR -> resources.getString(R.string.myr)
        ID_NOK -> resources.getString(R.string.nok)
        ID_NZD -> resources.getString(R.string.nzd)
        ID_PHP -> resources.getString(R.string.php)
        ID_PLN -> resources.getString(R.string.pln)
        ID_RON -> resources.getString(R.string.ron)
        ID_RUB -> resources.getString(R.string.rub)
        ID_SEK -> resources.getString(R.string.sek)
        ID_SGD -> resources.getString(R.string.sgd)
        ID_THB -> resources.getString(R.string.thb)
        ID_USD -> resources.getString(R.string.usd)
        ID_ZAR -> resources.getString(R.string.zar)
        else -> null
    }

    // TODO license
    private fun mapCurrencyIdToFlagResourceId(id: String) = when (id.toLowerCase()) {
        ID_AUD -> R.drawable.ic_australia
        ID_BGN -> R.drawable.ic_bulgaria
        ID_BRL -> R.drawable.ic_brazil
        ID_CAD -> R.drawable.ic_canada
        ID_CHF -> R.drawable.ic_switzerland
        ID_CNY -> R.drawable.ic_china
        ID_CZK -> R.drawable.ic_czech_republic
        ID_DKK -> R.drawable.ic_denmark
        ID_EUR -> R.drawable.ic_european_union
        ID_GBP -> R.drawable.ic_united_kingdom
        ID_HKD -> R.drawable.ic_hong_kong
        ID_HRK -> R.drawable.ic_croatia
        ID_HUF -> R.drawable.ic_hungary
        ID_IDR -> R.drawable.ic_indonesia
        ID_ILS -> R.drawable.ic_israel
        ID_INR -> R.drawable.ic_india
        ID_ISK -> R.drawable.ic_iceland
        ID_JPY -> R.drawable.ic_japan
        ID_KRW -> R.drawable.ic_south_korea
        ID_MXN -> R.drawable.ic_mexico
        ID_MYR -> R.drawable.ic_malaysia
        ID_NOK -> R.drawable.ic_norway
        ID_NZD -> R.drawable.ic_new_zealand
        ID_PHP -> R.drawable.ic_philippines
        ID_PLN -> R.drawable.ic_republic_of_poland
        ID_RON -> R.drawable.ic_romania
        ID_RUB -> R.drawable.ic_russia
        ID_SEK -> R.drawable.ic_sweden
        ID_SGD -> R.drawable.ic_singapore
        ID_THB -> R.drawable.ic_thailand
        ID_USD -> R.drawable.ic_united_states_of_america
        ID_ZAR -> R.drawable.ic_south_africa
        else -> null
    }

}