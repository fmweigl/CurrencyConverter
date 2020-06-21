package com.example.ratesapp.framework.datasource

import com.example.ratesapp.data.datasource.IRatesDataSource
import com.example.ratesapp.data.response.RatesResponse
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET

interface IRatesRetrofitDataSource : IRatesDataSource {

    @GET("/api/android/latest?base=EUR")
    override fun getRates(): Single<RatesResponse>

}