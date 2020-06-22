package com.example.ratesapp.usecase

import com.example.ratesapp.data.repository.UserSelectionsRepository

class SaveConversionInputUseCase(private val repository: UserSelectionsRepository) {

    fun saveConversionInput(input: String) = repository.saveConversionInput(input)

}