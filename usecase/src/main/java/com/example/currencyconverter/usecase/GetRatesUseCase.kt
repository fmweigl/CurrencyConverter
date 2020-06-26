package com.example.currencyconverter.usecase

import com.example.currencyconverter.data.repository.RatesRepository
import com.example.currencyconverter.domain.model.Rate
import com.example.currencyconverter.util.RxInterval
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import java.util.concurrent.TimeUnit

class GetRatesUseCase(private val repository: RatesRepository, private val rxInterval: RxInterval) {

    fun observeRates(baseCurrencyId: String): Observable<List<Rate>> =
        rxInterval.interval(1, TimeUnit.SECONDS)
            .flatMapSingle {
                repository.getRates(baseCurrencyId).onErrorResumeNext { Single.never() }
            }

}