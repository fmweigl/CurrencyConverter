package com.example.ratesapp.rates

data class CurrencyAdapterItem(
    val currencyId: String,
    val currencyName: String,
    val isBase: Boolean,
    val value: Float // needed?
)