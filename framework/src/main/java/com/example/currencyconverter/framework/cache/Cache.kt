package com.example.currencyconverter.framework.cache

import android.content.SharedPreferences
import com.example.currencyconverter.data.cache.ICache
import com.example.currencyconverter.data.response.RatesResponse
import com.google.gson.Gson
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.*

class RatesResponseCache(
    private val gson: Gson,
    private val sharedPrefs: SharedPreferences
) : ICache<RatesResponse> {

    override fun get(key: String): Single<Optional<RatesResponse>> = Single.fromCallable {
        val json = sharedPrefs.getString(key, null)
        json?.let { gson.fromJson(it, RatesResponse::class.java) }?.let { Optional.of(it) }
            ?: Optional.empty()
    }

    override fun put(key: String, t: RatesResponse): Completable = Completable.fromAction {
        sharedPrefs.edit().putString(key, gson.toJson(t)).apply()
    }
}