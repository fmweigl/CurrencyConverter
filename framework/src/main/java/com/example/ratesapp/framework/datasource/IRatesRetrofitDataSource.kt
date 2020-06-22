package com.example.ratesapp.framework.datasource

import com.example.ratesapp.data.datasource.IRatesDataSource
import com.example.ratesapp.data.response.RatesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface IRatesRetrofitDataSource : IRatesDataSource {

    @GET("/api/android/latest")
    override fun getRates(@Query("base") baseCurrencyId: String): Single<RatesResponse>

}