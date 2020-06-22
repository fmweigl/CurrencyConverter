package com.example.ratesapp.usecase

import com.example.ratesapp.data.repository.UserSelectionsRepository

class GetConversionInputUseCase(private val repository: UserSelectionsRepository) {

    fun getUserConversionInput() = repository.getConversionInput()

}