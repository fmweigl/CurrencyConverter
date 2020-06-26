package com.example.currencyconverter.usecase

import com.example.currencyconverter.data.repository.UserSelectionsRepository

class SaveConversionInputUseCase(private val repository: UserSelectionsRepository) {

    fun saveConversionInput(input: String) = repository.saveConversionInput(input)

}