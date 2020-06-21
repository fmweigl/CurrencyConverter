package com.example.ratesapp.rates

data class CurrencyAdapterItem(
    val currencyId: String,
    val currencyName: String?,
    val currencyFlagResId: Int?,
    val isBase: Boolean,
    val value: Float // TODO needed?
)