package com.example.ratesapp.usecase

import com.example.ratesapp.data.repository.UserSelectionsRepository

class GetCurrencySelectionUseCase(private val repository: UserSelectionsRepository) {

    fun getUserCurrencySelection() = repository.getCurrencySelection()

}