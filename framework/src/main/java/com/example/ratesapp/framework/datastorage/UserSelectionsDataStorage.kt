package com.example.ratesapp.framework.datastorage

import android.content.SharedPreferences
import com.example.ratesapp.data.datastorage.IUserSelectionsDataStorage
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import java.util.*

private const val KEY_CURRENCY_SELECTION = "KEY_CURRENCY_SELECTION"
private const val KEY_CONVERSION_INPUT = "KEY_CONVERSION_INPUT"

class UserSelectionsDataStorage(
    private val sharedPrefs: SharedPreferences
) : IUserSelectionsDataStorage {

    override fun saveCurrencySelection(currencyId: String): Completable =
        Completable.fromAction {
            sharedPrefs.edit().putString(KEY_CURRENCY_SELECTION, currencyId).apply()
        }

    override fun saveConversionInput(input: String): Completable =
        Completable.fromAction {
            sharedPrefs.edit().putString(KEY_CONVERSION_INPUT, input).apply()
        }

    override fun getCurrencySelection(): Single<Optional<String>> =
        Single.fromCallable {
            val savedString = sharedPrefs.getString(KEY_CURRENCY_SELECTION, null)
            savedString?.let { Optional.of(it) } ?: Optional.empty()
        }

    override fun getConversionInput(): Single<Optional<String>> =
        Single.fromCallable {
            val savedString = sharedPrefs.getString(KEY_CONVERSION_INPUT, null)
            savedString?.let { Optional.of(it) } ?: Optional.empty()
        }
}