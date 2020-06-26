package com.example.currencyconverter

import android.app.Application
import android.content.Context
import com.example.currencyconverter.data.cache.ICache
import com.example.currencyconverter.data.datasource.IRatesDataSource
import com.example.currencyconverter.data.datastorage.IUserSelectionsDataStorage
import com.example.currencyconverter.data.repository.RatesRepository
import com.example.currencyconverter.data.repository.UserSelectionsRepository
import com.example.currencyconverter.data.response.RatesResponse
import com.example.currencyconverter.data.response.RatesResponseDeserializer
import com.example.currencyconverter.framework.cache.RatesResponseCache
import com.example.currencyconverter.framework.datasource.IRatesRetrofitDataSource
import com.example.currencyconverter.framework.datastorage.UserSelectionsDataStorage
import com.example.currencyconverter.rates.NumberFormatter
import com.example.currencyconverter.rates.RatesToAdapterItemMapper
import com.example.currencyconverter.rates.RatesViewModel
import com.example.currencyconverter.usecase.*
import com.example.currencyconverter.util.RxInterval
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

private val REVOLUT_BASE_URL = "https://hiring.revolut.codes"
private val KEY_SHARED_PREFS = "KEY_SHARED_PREFS"

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
        single { getSharedPreferences(KEY_SHARED_PREFS, Context.MODE_PRIVATE) }
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
        single { RatesRepository(get(), get()) }
        single<ICache<RatesResponse>> { RatesResponseCache(get(), get()) }
        factory { GetRatesUseCase(get(), get()) }
        factory { RxInterval() }
        factory { RatesViewModel(get(), get(), get(), get(), get(), get()) }
        factory { RatesToAdapterItemMapper(resources, get()) }
        factory { NumberFormatter() }
        single { UserSelectionsRepository(get()) }
        single<IUserSelectionsDataStorage> { UserSelectionsDataStorage(get()) }
        factory { GetConversionInputUseCase(get()) }
        factory { GetCurrencySelectionUseCase(get()) }
        factory { SaveConversionInputUseCase(get()) }
        factory { SaveCurrencySelectionUseCase(get()) }
        factory { Gson() }
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(module))
        }
    }

}