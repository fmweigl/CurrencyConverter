package com.example.ratesapp

import android.app.Application
import android.content.Context
import com.example.ratesapp.data.datasource.IRatesDataSource
import com.example.ratesapp.data.datastorage.IUserSelectionsDataStorage
import com.example.ratesapp.data.repository.RatesRepository
import com.example.ratesapp.data.repository.UserSelectionsRepository
import com.example.ratesapp.data.response.RatesResponse
import com.example.ratesapp.data.response.RatesResponseDeserializer
import com.example.ratesapp.framework.datasource.IRatesRetrofitDataSource
import com.example.ratesapp.framework.datastorage.UserSelectionsDataStorage
import com.example.ratesapp.rates.NumberFormatter
import com.example.ratesapp.rates.RatesToAdapterItemMapper
import com.example.ratesapp.rates.RatesViewModel
import com.example.ratesapp.usecase.*
import com.example.ratesapp.util.RxInterval
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
        single { RatesRepository(get()) }
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
    }

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(module))
        }
    }

}