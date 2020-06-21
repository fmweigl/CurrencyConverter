package com.example.ratesapp.data.response

import com.google.gson.annotations.SerializedName

data class RatesResponse(
    @SerializedName("baseCurrency")
    val baseCurrency: String
)