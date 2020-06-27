package com.example.currencyconverter.rates

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.currencyconverter.domain.CurrencyIds
import com.example.currencyconverter.domain.model.Rate
import com.example.currencyconverter.usecase.*
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.android.plugins.RxAndroidPlugins
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.math.BigDecimal
import java.util.*

class RatesViewModelTest {

    @Rule
    @JvmField
    var rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getRatesUseCase: GetRatesUseCase

    @Mock
    private lateinit var getUserCurrencySelectionUseCase: GetCurrencySelectionUseCase

    @Mock
    private lateinit var getUserConversionInputUseCase: GetConversionInputUseCase

    @Mock
    private lateinit var saveUserCurrencySelectionUseCase: SaveCurrencySelectionUseCase

    @Mock
    private lateinit var saveConversionInputUseCase: SaveConversionInputUseCase

    @Mock
    private lateinit var mapper: RatesToAdapterItemMapper

    @InjectMocks
    private lateinit var tested: RatesViewModel

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxAndroidPlugins.setInitMainThreadSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun tearDown() = RxAndroidPlugins.setInitMainThreadSchedulerHandler { null }

    @Test
    fun `should observe rates with saved input and saved selected currency and map and display them`() {
        val selectedCurrency = "selectedCurrency"
        given(getUserCurrencySelectionUseCase.getUserCurrencySelection())
            .willReturn(Single.just(Optional.of(selectedCurrency)))
        val input = "input"
        given(getUserConversionInputUseCase.getUserConversionInput())
            .willReturn(Single.just(Optional.of(input)))
        val rates = listOf(
            Rate(currencyId = "selectedCurrency", exchangeValue = BigDecimal.valueOf(1)),
            Rate(currencyId = "currency0", exchangeValue = BigDecimal.valueOf(2)),
            Rate(currencyId = "currency1", exchangeValue = BigDecimal.valueOf(3))
        )
        given(getRatesUseCase.observeRates(selectedCurrency)).willReturn(Observable.just(rates))
        val adapterItems = listOf(
            CurrencyAdapterItem("0", null, null, "", true),
            CurrencyAdapterItem("1", null, null, "", false),
            CurrencyAdapterItem("2", null, null, "", false)
        )
        given(mapper.map(input, rates)).willReturn(adapterItems)

        tested.resumeUpdatingRates()

        assertEquals(adapterItems, tested.adapterItems.value)
    }

    @Test
    fun `should use default currency 'EUR' when no selected currency saved`() {
        given(getUserCurrencySelectionUseCase.getUserCurrencySelection())
            .willReturn(Single.just(Optional.empty()))
        given(getUserConversionInputUseCase.getUserConversionInput())
            .willReturn(Single.just(Optional.of("")))
        given(getRatesUseCase.observeRates(any())).willReturn(Observable.empty())

        tested.resumeUpdatingRates()

        then(getRatesUseCase).should().observeRates(CurrencyIds.ID_EUR)
    }

    @Test
    fun `should use default input '1' when no conversion input saved`() {
        given(getUserConversionInputUseCase.getUserConversionInput())
            .willReturn(Single.just(Optional.empty()))
        given(getUserCurrencySelectionUseCase.getUserCurrencySelection())
            .willReturn(Single.just(Optional.of("USD")))
        given(getRatesUseCase.observeRates(any())).willReturn(Observable.just(emptyList()))

        tested.resumeUpdatingRates()

        then(mapper).should().map(eq("1"), any())
    }

    @Test
    fun `should move newly selected currency to top when clicked`() {
        mockNoInputOrCurrencySaved()
        mockSavingDataCompletes()
        val rates = listOf(
            Rate("0", BigDecimal.valueOf(1)),
            Rate("1", BigDecimal.valueOf(2)),
            Rate("2", BigDecimal.valueOf(3))
        )
        given(getRatesUseCase.observeRates(any())).willReturn(Observable.just(rates))
        val adapterItemToClick = CurrencyAdapterItem("2", null, null, "value3", false)
        val adapterItems = listOf(
            CurrencyAdapterItem("0", null, null, "value1", true),
            CurrencyAdapterItem("1", null, null, "value2", false),
            adapterItemToClick
        )
        given(mapper.map(any(), any())).willReturn(adapterItems)
        val reorderedAdapterItems =
            listOf(adapterItemToClick).plus(adapterItems[0]).plus(adapterItems[1])
        given(mapper.moveCurrencyToTopOfAdapterItems(adapterItemToClick.currencyId, adapterItems))
            .willReturn(reorderedAdapterItems)
        tested.resumeUpdatingRates()
        given(getRatesUseCase.observeRates(any())).willReturn(Observable.never())

        tested.onCurrencyClicked(adapterItemToClick.currencyId)

        assertEquals(reorderedAdapterItems, tested.adapterItems.value)
    }

    @Test
    fun `should save adapter item value as new conversion input when clicked`() {
        mockNoInputOrCurrencySaved()
        mockSavingDataCompletes()
        given(getRatesUseCase.observeRates(any())).willReturn(Observable.just(emptyList()))
        val adapterItems = listOf(
            CurrencyAdapterItem("0", null, null, "value1", true),
            CurrencyAdapterItem("1", null, null, "value2", false)
        )
        given(mapper.map(any(), any())).willReturn(adapterItems)
        given(mapper.moveCurrencyToTopOfAdapterItems(any(), any())).willReturn(adapterItems)
        tested.resumeUpdatingRates()

        tested.onCurrencyClicked(adapterItems.first().currencyId)

        then(saveConversionInputUseCase).should()
            .saveConversionInput(adapterItems.first().displayValue)
    }

    @Test
    fun `should save selected currency when clicked`() {
        mockNoInputOrCurrencySaved()
        mockSavingDataCompletes()
        given(getRatesUseCase.observeRates(any())).willReturn(Observable.just(emptyList()))
        val adapterItems = listOf(
            CurrencyAdapterItem("0", null, null, "value1", true),
            CurrencyAdapterItem("1", null, null, "value2", false)
        )

        given(mapper.map(any(), any())).willReturn(adapterItems)
        given(mapper.moveCurrencyToTopOfAdapterItems(any(), any())).willReturn(adapterItems)
        tested.resumeUpdatingRates()

        tested.onCurrencyClicked("0")

        then(saveUserCurrencySelectionUseCase).should().saveUserCurrencySelection("0")
    }

    @Test
    fun `should save input when input made`() {
        mockNoInputOrCurrencySaved()
        mockSavingDataCompletes()
        given(getRatesUseCase.observeRates(any())).willReturn(Observable.never())
        val input = "input"

        tested.onConversionInput(input)

        then(saveConversionInputUseCase).should().saveConversionInput(input)
    }

    @Test
    fun `should re-map latest rates when input made`() {
        mockSavingDataCompletes()
        val oldInput = "oldInput"
        given(getUserConversionInputUseCase.getUserConversionInput())
            .willReturn(Single.just(Optional.of(oldInput)))
        given(getUserCurrencySelectionUseCase.getUserCurrencySelection())
            .willReturn(Single.just(Optional.empty()))
        val input = "input"
        val rates = listOf(Rate("", BigDecimal.valueOf(1)))
        given(getRatesUseCase.observeRates(any())).willReturn(Observable.just(rates))
        val adapterItems = listOf(mock<CurrencyAdapterItem>())
        given(mapper.map(oldInput, rates)).willReturn(emptyList())
        given(mapper.map(input, rates)).willReturn(adapterItems)
        tested.resumeUpdatingRates()
        given(getRatesUseCase.observeRates(any())).willReturn(Observable.never())

        tested.onConversionInput(input)

        assertEquals(adapterItems, tested.adapterItems.value)
    }

    private fun mockNoInputOrCurrencySaved() {
        given(getUserConversionInputUseCase.getUserConversionInput())
            .willReturn(Single.just(Optional.empty()))
        given(getUserCurrencySelectionUseCase.getUserCurrencySelection())
            .willReturn(Single.just(Optional.empty()))
    }

    private fun mockSavingDataCompletes() {
        given(saveUserCurrencySelectionUseCase.saveUserCurrencySelection(any()))
            .willReturn(Completable.complete())
        given(saveConversionInputUseCase.saveConversionInput(any())).willReturn(Completable.complete())
    }

}