package com.example.ratesapp.data.repository

import com.example.ratesapp.data.datasource.IRatesDataSource
import com.example.ratesapp.data.response.RatesResponse
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.then
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class RatesRepositoryTest {

    @Mock
    private lateinit var dataSource: IRatesDataSource

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
        val response = RatesResponse(baseCurrency = baseCurrency)
        given(dataSource.getRates()).willReturn(Single.just(response))

        tested.getRates()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue {
                it.baseCurrency == baseCurrency
            }
        then(dataSource).should().getRates()
    }

}