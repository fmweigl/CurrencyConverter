package com.example.currencyconverter.usecase

import com.example.currencyconverter.data.repository.UserSelectionsRepository

class GetConversionInputUseCase(private val repository: UserSelectionsRepository) {

    fun getUserConversionInput() = repository.getConversionInput()

}