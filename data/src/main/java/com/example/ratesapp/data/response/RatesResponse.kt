package com.example.ratesapp.data.response

import java.math.BigDecimal

data class RatesResponse(
    val baseCurrency: String,
    val currencyRates: List<RateResponse>
)

data class RateResponse(
    val currencyId: String,
    val value: BigDecimal
)