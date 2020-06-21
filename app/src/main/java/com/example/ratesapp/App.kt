package com.example.ratesapp

import android.app.Application
import com.example.ratesapp.data.datasource.IRatesDataSource
import com.example.ratesapp.data.repository.RatesRepository
import com.example.ratesapp.data.response.RatesResponse
import com.example.ratesapp.data.response.RatesResponseDeserializer
import com.example.ratesapp.framework.datasource.IRatesRetrofitDataSource
import com.example.ratesapp.rates.RatesToAdapterItemMapper
import com.example.ratesapp.rates.RatesViewModel
import com.example.ratesapp.usecase.GetRatesUseCase
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private val REVOLUT_BASE_URL = "https://hiring.revolut.codes"

class App : Application() {

    private val module = module {
        single {
            OkHttpClient.Builder()
                .addNetworkInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
                .build()
        }
        single<IRatesDataSource> {
            val gson = GsonBuilder().registerTypeAdapter(
                RatesResponse::class.java,
                RatesResponseDeserializer()
            ).create()
            Retrofit.Builder()
                .client(get())
                .baseUrl(REVOLUT_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build()
                .create(IRatesRetrofitDataSource::class.java)
        }
        single { RatesRepository(get()) }
        factory { GetRatesUseCase(get()) }
        factory { RatesViewModel(get(), get()) }
        factory { RatesToAdapterItemMapper(resources) }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(module))
        }
    }

}