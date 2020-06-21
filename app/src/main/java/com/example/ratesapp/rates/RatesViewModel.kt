package com.example.ratesapp.rates

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.ratesapp.usecase.GetRatesUseCase

class RatesViewModel(private val getRatesUseCase: GetRatesUseCase) : ViewModel() {

    init {
        updateRates()
    }

    private fun updateRates() {
        getRatesUseCase.getRates().subscribe({
            Log.d("yyy", it.toString())
        }, {
            it.printStackTrace()
            Log.e("yyy", it.toString())
        })
    }
}