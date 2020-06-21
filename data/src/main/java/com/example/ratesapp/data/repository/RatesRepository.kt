package com.example.ratesapp.data.repository

import com.example.ratesapp.data.datasource.IRatesDataSource
import com.example.ratesapp.data.response.RatesResponse
import com.example.ratesapp.domain.model.Rates
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class RatesRepository(private val dataSource: IRatesDataSource) {

    fun getRates(): Single<Rates> = dataSource.getRates()
        .map { it.toRates() }
        .subscribeOn(Schedulers.io())

    private fun RatesResponse.toRates() = Rates(baseCurrency = baseCurrency, currencyRates = currencyRates)

}