package com.example.currencyconverter.usecase

import com.example.currencyconverter.data.repository.UserSelectionsRepository
import com.nhaarman.mockitokotlin2.given
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class GetCurrencySelectionUseCaseTest {

    @Mock
    private lateinit var repository: UserSelectionsRepository

    @InjectMocks
    private lateinit var tested: GetCurrencySelectionUseCase

    @Before
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun `should get currency selection`() {
        val currency = Optional.of("currency")
        given(repository.getCurrencySelection()).willReturn(Single.just(currency))

        tested.getUserCurrencySelection()
            .test()
            .assertNoErrors()
            .assertValue(currency)
    }
}