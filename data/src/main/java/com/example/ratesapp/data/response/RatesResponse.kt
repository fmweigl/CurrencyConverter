package com.example.ratesapp.data.response

data class RatesResponse(
    val baseCurrency: String,
    val currencyRates: List<Pair<String, Float>>
)