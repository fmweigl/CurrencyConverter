package com.example.ratesapp.usecase

import com.example.ratesapp.data.repository.RatesRepository
import com.example.ratesapp.domain.model.Rate
import com.example.ratesapp.util.RxInterval
import io.reactivex.rxjava3.core.Observable
import java.util.concurrent.TimeUnit

class GetRatesUseCase(private val repository: RatesRepository, private val rxInterval: RxInterval) {

    fun observeRates(baseCurrencyId: String): Observable<List<Rate>> =
        rxInterval.interval(1, TimeUnit.SECONDS)
            .flatMapSingle { repository.getRates(baseCurrencyId) } // TODO onerror

}