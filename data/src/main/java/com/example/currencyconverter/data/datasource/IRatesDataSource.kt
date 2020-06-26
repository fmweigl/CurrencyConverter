package com.example.currencyconverter.data.datasource

import com.example.currencyconverter.data.response.RatesResponse
import io.reactivex.rxjava3.core.Single

interface IRatesDataSource {

    fun getRates(baseCurrencyId: String): Single<RatesResponse>

}