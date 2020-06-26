package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.datastorage.IUserSelectionsDataStorage

class UserSelectionsRepository(private val userSelectionsDataStorage: IUserSelectionsDataStorage) {

    fun saveCurrencySelection(currencyId: String) =
        userSelectionsDataStorage.saveCurrencySelection(currencyId)

    fun saveConversionInput(input: String) =
        userSelectionsDataStorage.saveConversionInput(input)

    fun getCurrencySelection() = userSelectionsDataStorage.getCurrencySelection()

    fun getConversionInput() = userSelectionsDataStorage.getConversionInput()

}