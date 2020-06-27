package com.example.currencyconverter.rates

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.currencyconverter.domain.CurrencyIds
import com.example.currencyconverter.domain.model.Rate
import com.example.currencyconverter.usecase.*
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable

private const val DEFAULT_BASE_CURRENCY_ID = CurrencyIds.ID_EUR
private const val DEFAULT_CONVERSION_INPUT = "1"

class RatesViewModel(
    private val getRatesUseCase: GetRatesUseCase,
    private val getCurrencySelectionUseCase: GetCurrencySelectionUseCase,
    private val getConversionInputUseCase: GetConversionInputUseCase,
    private val saveCurrencySelectionUseCase: SaveCurrencySelectionUseCase,
    private val saveConversionInputUseCase: SaveConversionInputUseCase,
    private val mapper: RatesToAdapterItemMapper
) : ViewModel(), IConversionClickedListener, IConversionInputListener {

    private val _adapterItems = MutableLiveData<List<CurrencyAdapterItem>>()
    val adapterItems: LiveData<List<CurrencyAdapterItem>> = _adapterItems

    private var updateRatesDisposable: Disposable? = null
    private val compositeDisposable = CompositeDisposable()
    private var latestRates = listOf<Rate>()

    fun resumeUpdatingRates() {
        updateRatesDisposable?.dispose()

        updateRatesDisposable = getBaseCurrencySelectionSingle()
            .flatMapObservable { getRatesUseCase.observeRates(it) }
            .doOnNext { rates -> latestRates = rates }
            .flatMap { rates ->
                getConversionInputSingle().toObservable().map { input -> input to rates }
            }
            .map { inputToRates -> mapper.map(inputToRates.first, inputToRates.second) }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                _adapterItems.value = it
            }
    }

    fun pauseUpdatingRates() = updateRatesDisposable?.dispose()

    private fun getBaseCurrencySelectionSingle() =
        getCurrencySelectionUseCase.getUserCurrencySelection().map {
            if (it.isPresent) it.get() else DEFAULT_BASE_CURRENCY_ID
        }

    private fun getConversionInputSingle() =
        getConversionInputUseCase.getUserConversionInput().map {
            if (it.isPresent) it.get() else DEFAULT_CONVERSION_INPUT
        }

    override fun onCurrencyClicked(currencyId: String) {
        pauseUpdatingRates()
        latestRates = emptyList()
        // we manually move the newly selected base currency to the top of the list so it moves to the top right away instead of with the next api response
        adapterItems.value?.let {
            _adapterItems.value = mapper.moveCurrencyToTopOfAdapterItems(currencyId, it)
        }
        // the value of the newly selected base currency becomes the new conversion input
        val newInput = adapterItems.value?.firstOrNull()?.displayValue ?: ""
        // save selected currency and new user input, then start updating rates again
        val updateBaseCurrencyDisposable =
            saveCurrencySelectionUseCase.saveUserCurrencySelection(currencyId)
                .concatWith(saveConversionInputUseCase.saveConversionInput(newInput))
                .subscribe({ resumeUpdatingRates() }, { it.printStackTrace() })
        compositeDisposable.add(updateBaseCurrencyDisposable)
    }

    override fun onConversionInput(input: String) {
        val updateInputDisposable = saveConversionInputUseCase.saveConversionInput(input)
            .toSingle {
                mapper.map(input, latestRates)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ items ->
                if (items.isNotEmpty()) _adapterItems.value = items
            }, {
                it.printStackTrace()
            })
        compositeDisposable.add(updateInputDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        updateRatesDisposable?.dispose()
        compositeDisposable.clear()
    }
}


