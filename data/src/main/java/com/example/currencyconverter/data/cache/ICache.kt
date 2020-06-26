package com.example.currencyconverter.data.cache

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.*

interface ICache<T> {

    fun get(key: String): Single<Optional<T>>

    fun put(key: String, t: T): Completable

}