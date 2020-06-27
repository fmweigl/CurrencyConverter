package com.example.currencyconverter.data.repository

import com.example.currencyconverter.data.cache.ICache
import com.example.currencyconverter.data.datasource.IRatesDataSource
import com.example.currencyconverter.data.response.RateResponse
import com.example.currencyconverter.data.response.RatesResponse
import com.example.currencyconverter.domain.model.Rate
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.then
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.math.BigDecimal
import java.util.*

class RatesRepositoryTest {

    @Mock
    private lateinit var dataSource: IRatesDataSource

    @Mock
    private lateinit var cache: ICache<RatesResponse>

    @InjectMocks
    private lateinit var tested: RatesRepository

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun tearDown() = RxJavaPlugins.setIoSchedulerHandler { null }

    @Test
    fun `should get and convert rates`() {
        val baseCurrency = "baseCurrency"
        val currencyId0 = "currencyId0"
        val currencyId1 = "currencyId1"
        val currencyRate0 = BigDecimal.valueOf(1)
        val currencyRate1 = BigDecimal.valueOf(2)
        val rateResponse0 = RateResponse(currencyId0, currencyRate0)
        val rateResponse1 = RateResponse(currencyId1, currencyRate1)
        val response = RatesResponse(
            baseCurrency = baseCurrency,
            currencyRates = listOf(rateResponse0, rateResponse1)
        )
        given(dataSource.getRates(baseCurrency)).willReturn(Single.just(response))
        given(cache.put(any(), any())).willReturn(Completable.complete())


        val expectedRates = listOf(
            Rate(baseCurrency, BigDecimal.valueOf(1)),
            Rate(currencyId0, currencyRate0),
            Rate(currencyId1, currencyRate1)
        )

        tested.getRates(baseCurrency)
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue(expectedRates)
        then(dataSource).should().getRates(baseCurrency)
    }

    @Test
    fun `should put response to cache`() {
        val currencyId = "USD"
        val response = RatesResponse("", emptyList())
        given(dataSource.getRates(any())).willReturn(Single.just(response))
        given(cache.put(any(), any())).willReturn(Completable.complete())

        tested.getRates(currencyId)
            .test()
            .assertNoErrors()
            .assertComplete()

        then(cache).should().put(currencyId, response)
    }

    @Test
    fun `should return response from cache if fetching from datasource fails`() {
        val currencyId = "EUR"
        val cachedResponse =
            RatesResponse(currencyId, listOf(RateResponse("USD", BigDecimal.valueOf(2))))
        given(cache.get(currencyId)).willReturn(Single.just(Optional.of(cachedResponse)))
        given(dataSource.getRates(currencyId)).willReturn(Single.error(Throwable()))

        tested.getRates("EUR")
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue {
                it == listOf(
                    Rate("EUR", BigDecimal.valueOf(1)),
                    Rate("USD", BigDecimal.valueOf(2))
                )
            }
    }

    @Test
    fun `should throw error when fetching from datasource failed and no response in cache`() {
        val throwable = Throwable()
        given(dataSource.getRates(any())).willReturn(Single.error(throwable))
        given(cache.get(any())).willReturn(Single.just(Optional.empty()))

        tested.getRates("")
            .test()
            .assertError(throwable)
    }

}