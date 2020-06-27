package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.cache.ICache
import com.example.currencyconverter.data.datasource.IRatesDataSource
import com.example.currencyconverter.data.response.RateResponse
import com.example.currencyconverter.data.response.RatesResponse
import com.example.currencyconverter.domain.model.Rate
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import java.math.BigDecimal

private const val KEY_CACHED_RESPONSE = "KEY_CACHED_RESPONSE"

class RatesRepository(
    private val dataSource: IRatesDataSource,
    private val cache: ICache<RatesResponse>
) {

    fun getRates(baseCurrencyId: String): Single<List<Rate>> = dataSource.getRates(baseCurrencyId)
        .concatMap { cache.put(KEY_CACHED_RESPONSE, it).toSingle { it } }
        .onErrorResumeNext { throwable ->
            cache.get(KEY_CACHED_RESPONSE).map { responseOptional ->
                if (responseOptional.isPresent) responseOptional.get() else throw throwable
            }
        }
        .map { it.toRates() }
        .subscribeOn(Schedulers.io())

    private fun RatesResponse.toRates(): List<Rate> {
        val baseCurrencyRate = Rate(
            currencyId = baseCurrency,
            exchangeValue = BigDecimal.valueOf(1) // base currency always has a rate of 1
        )
        return listOf(baseCurrencyRate).plus(currencyRates.map { it.toRate() })
    }

    private fun RateResponse.toRate() = Rate(currencyId, value)

}