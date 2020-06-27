package com.example.currencyconverter.domain.model

import java.math.BigDecimal

data class Rate(
    val currencyId: String,
    val exchangeValue: BigDecimal
)