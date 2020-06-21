package com.example.ratesapp.usecase

import com.example.ratesapp.data.repository.RatesRepository

class GetRatesUseCase(private val repository: RatesRepository) {

    fun getRates() = repository.getRates()

}