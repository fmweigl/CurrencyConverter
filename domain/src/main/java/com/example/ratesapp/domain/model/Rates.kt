package com.example.ratesapp.domain.model

data class Rates(
    val baseCurrency: String,
    val currencyRates: List<Pair<String, Float>> // TODO
)