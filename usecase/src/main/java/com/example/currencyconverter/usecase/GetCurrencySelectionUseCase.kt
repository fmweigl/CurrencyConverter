package com.example.currencyconverter.usecase

import com.example.currencyconverter.data.repository.UserSelectionsRepository

class GetCurrencySelectionUseCase(private val repository: UserSelectionsRepository) {

    fun getUserCurrencySelection() = repository.getCurrencySelection()

}