package com.example.ratesapp.domain.model

import java.math.BigDecimal

data class Rate(
    val currencyId: String,
    val value: BigDecimal
)