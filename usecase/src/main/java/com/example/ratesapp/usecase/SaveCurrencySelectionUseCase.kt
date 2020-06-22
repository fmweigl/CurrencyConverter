package com.example.ratesapp.usecase

import com.example.ratesapp.data.repository.UserSelectionsRepository

class SaveCurrencySelectionUseCase(private val repository: UserSelectionsRepository) {

    fun saveUserCurrencySelection(currencyId: String) =
        repository.saveCurrencySelection(currencyId)

}