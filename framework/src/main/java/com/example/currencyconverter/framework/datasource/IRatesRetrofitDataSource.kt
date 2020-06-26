package com.example.currencyconverter.framework.datasource

import com.example.currencyconverter.data.datasource.IRatesDataSource
import com.example.currencyconverter.data.response.RatesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface IRatesRetrofitDataSource : IRatesDataSource {

    @GET("/api/android/latest")
    override fun getRates(@Query("base") baseCurrencyId: String): Single<RatesResponse>

}