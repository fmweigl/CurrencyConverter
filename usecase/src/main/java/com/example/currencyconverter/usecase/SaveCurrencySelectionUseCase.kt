package com.example.currencyconverter.usecase

import com.example.currencyconverter.data.repository.UserSelectionsRepository

class SaveCurrencySelectionUseCase(private val repository: UserSelectionsRepository) {

    fun saveUserCurrencySelection(currencyId: String) =
        repository.saveCurrencySelection(currencyId)

}