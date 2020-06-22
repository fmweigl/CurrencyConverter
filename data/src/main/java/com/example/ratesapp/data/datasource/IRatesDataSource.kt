package com.example.ratesapp.data.datasource

import com.example.ratesapp.data.response.RatesResponse
import io.reactivex.rxjava3.core.Single

interface IRatesDataSource {

    fun getRates(baseCurrencyId: String): Single<RatesResponse>

}