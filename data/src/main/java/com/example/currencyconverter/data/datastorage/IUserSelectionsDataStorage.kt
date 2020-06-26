package com.example.currencyconverter.data.datastorage

import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.*

interface IUserSelectionsDataStorage {

    fun saveCurrencySelection(currencyId: String): Completable

    fun saveConversionInput(input: String): Completable

    fun getCurrencySelection(): Single<Optional<String>>

    fun getConversionInput(): Single<Optional<String>>

}