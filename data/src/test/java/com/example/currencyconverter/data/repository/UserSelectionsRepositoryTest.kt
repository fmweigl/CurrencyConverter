package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.datastorage.IUserSelectionsDataStorage
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.then
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class UserSelectionsRepositoryTest {

    @Mock
    private lateinit var userSelectionsDataStorage: IUserSelectionsDataStorage

    @InjectMocks
    private lateinit var tested: UserSelectionsRepository

    @Before
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun `should save currency selection`() {
        val currency = "currency"
        given(userSelectionsDataStorage.saveCurrencySelection(currency)).willReturn(Completable.complete())

        tested.saveCurrencySelection(currency)
            .test()
            .assertNoErrors()

        then(userSelectionsDataStorage).should().saveCurrencySelection(currency)
    }

    @Test
    fun `should save conversion input`() {
        val input = "input"
        given(userSelectionsDataStorage.saveConversionInput(input)).willReturn(Completable.complete())

        tested.saveConversionInput(input)
            .test()
            .assertNoErrors()

        then(userSelectionsDataStorage).should().saveConversionInput(input)
    }

    @Test
    fun `should get currency selection`() {
        val currency = Optional.of("currency")
        given(userSelectionsDataStorage.getCurrencySelection()).willReturn(Single.just(currency))

        tested.getCurrencySelection()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue(currency)
    }

    @Test
    fun `should get conversion input`() {
        val input = Optional.of("input")
        given(userSelectionsDataStorage.getConversionInput()).willReturn(Single.just(input))

        tested.getConversionInput()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue(input)
    }

}