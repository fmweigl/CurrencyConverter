package com.example.ratesapp.rates

import android.content.res.Resources
import com.example.ratesapp.R
import com.example.ratesapp.domain.model.Rates

class RatesToAdapterItemMapper(private val resources: Resources) {

    fun map(rates: Rates): List<CurrencyAdapterItem> {
        val baseCurrencyItem =
            CurrencyAdapterItem(
                currencyId = rates.baseCurrency,
                currencyName = mapCurrencyIdToName(rates.baseCurrency),
                isBase = true,
                value = 1f
            )
        val otherCurrencyAdapterItems = rates.currencyRates.map {
            CurrencyAdapterItem(
                currencyId = it.first,
                currencyName = mapCurrencyIdToName(it.first),
                isBase = false,
                value = it.second
            )
        }

        return listOf(baseCurrencyItem).plus(otherCurrencyAdapterItems)
    }

    private fun mapCurrencyIdToName(id: String): String = when (id.toLowerCase()) {
        "aud" -> resources.getString(R.string.aud)
        "bgn" -> resources.getString(R.string.bgn)
        "brl" -> resources.getString(R.string.brl)
        "cad" -> resources.getString(R.string.cad)
        "chf" -> resources.getString(R.string.chf)
        "cny" -> resources.getString(R.string.cny)
        "czk" -> resources.getString(R.string.czk)
        "dkk" -> resources.getString(R.string.dkk)
        "eur" -> resources.getString(R.string.eur)
        "gbp" -> resources.getString(R.string.gbp)
        "hkd" -> resources.getString(R.string.hkd)
        "hrk" -> resources.getString(R.string.hrk)
        "huf" -> resources.getString(R.string.huf)
        "idr" -> resources.getString(R.string.idr)
        "ils" -> resources.getString(R.string.ils)
        "inr" -> resources.getString(R.string.inr)
        "isk" -> resources.getString(R.string.isk)
        "jpy" -> resources.getString(R.string.jpy)
        "krw" -> resources.getString(R.string.krw)
        "mxn" -> resources.getString(R.string.mxn)
        "myr" -> resources.getString(R.string.myr)
        "nok" -> resources.getString(R.string.nok)
        "nzd" -> resources.getString(R.string.nzd)
        "php" -> resources.getString(R.string.php)
        "pln" -> resources.getString(R.string.pln)
        "ron" -> resources.getString(R.string.ron)
        "rub" -> resources.getString(R.string.rub)
        "sek" -> resources.getString(R.string.sek)
        "sgd" -> resources.getString(R.string.sgd)
        "thb" -> resources.getString(R.string.thb)
        "usd" -> resources.getString(R.string.usd)
        "zar" -> resources.getString(R.string.zar)
        else -> ""
    }

}