package com.example.ratesapp.rates

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ratesapp.usecase.GetRatesUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers

class RatesViewModel(
    private val getRatesUseCase: GetRatesUseCase,
    private val mapper: RatesToAdapterItemMapper
) : ViewModel() {

    private val _adapterItems = MutableLiveData<List<CurrencyAdapterItem>>()
    val adapterItems: LiveData<List<CurrencyAdapterItem>> = _adapterItems

    init {
        updateRates()
    }

    private fun updateRates() {
        getRatesUseCase.getRates()
            .map { mapper.map(it) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                _adapterItems.value = it
                Log.d("yyy", it.toString())
            }, {
                it.printStackTrace()
                Log.e("yyy", it.toString())
            })
    }
}