package com.example.ratesapp.rates

import androidx.annotation.DrawableRes

data class CurrencyAdapterItem(
    val currencyId: String,
    val currencyName: String?,
    @DrawableRes val countryFlagResId: Int?,
    val value: String,
    val isBase: Boolean
) {

    fun equalsIgnoringValue(other: CurrencyAdapterItem): Boolean {
        return currencyId == other.currencyId &&
                currencyName == other.currencyName &&
                countryFlagResId == other.countryFlagResId &&
                isBase == other.isBase
    }

}