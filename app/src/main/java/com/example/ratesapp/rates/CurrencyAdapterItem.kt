package com.example.ratesapp.rates

import androidx.annotation.DrawableRes

data class CurrencyAdapterItem(
    val currencyId: String,
    val currencyName: String?,
    @DrawableRes val countryFlagResId: Int?,
    val isBase: Boolean,
    val value: Float // TODO needed?
)