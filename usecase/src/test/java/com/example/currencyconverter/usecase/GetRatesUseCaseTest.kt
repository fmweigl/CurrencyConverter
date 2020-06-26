package com.example.currencyconverter.usecase

import com.example.currencyconverter.data.repository.RatesRepository
import com.example.currencyconverter.domain.model.Rate
import com.example.currencyconverter.util.RxInterval
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.concurrent.TimeUnit

class GetRatesUseCaseTest {

    @Mock
    private lateinit var repository: RatesRepository

    @Mock
    private lateinit var rxInterval: RxInterval

    @InjectMocks
    private lateinit var tested: GetRatesUseCase

    @Before
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun `should observe rates at one second interval`() {
        val currencyId = "currencyId"
        val rates0 = listOf(mock<Rate>())
        val rates1 = listOf(mock<Rate>())
        val rates2 = listOf(mock<Rate>())
        given(repository.getRates(currencyId)).willReturn(
            Single.just(rates0),
            Single.just(rates1),
            Single.just(rates2)
        )
        given(rxInterval.interval(1, TimeUnit.SECONDS)).willReturn(Observable.just(1L, 2L, 3L))

        tested.observeRates(currencyId)
            .test()
            .assertNoErrors()
            .assertValues(rates0, rates1, rates2)
    }

    @Test
    fun `should ignore errors`() {
        val currencyId = "currencyId"
        val rates0 = listOf(mock<Rate>())
        val rates1 = listOf(mock<Rate>())
        val rates2 = listOf(mock<Rate>())
        given(repository.getRates(currencyId)).willReturn(
            Single.just(rates0),
            Single.error(Throwable()),
            Single.just(rates1),
            Single.just(rates2)
        )
        given(rxInterval.interval(1, TimeUnit.SECONDS)).willReturn(Observable.just(1L, 2L, 3L, 4L))

        tested.observeRates(currencyId)
            .test()
            .assertValues(rates0, rates1, rates2)
    }

}