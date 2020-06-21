package com.example.ratesapp.usecase

import com.example.ratesapp.data.repository.RatesRepository
import com.example.ratesapp.domain.model.Rates
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.then
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class GetRatesUseCaseTest {

    @Mock
    private lateinit var repository: RatesRepository

    @InjectMocks
    private lateinit var tested: GetRatesUseCase

    @Before
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun `should get rates`() {
        val rates = mock<Rates>()
        given(repository.getRates()).willReturn(Single.just(rates))

        tested.getRates()
            .test()
            .assertComplete()
            .assertNoErrors()
            .assertValue(rates)
        then(repository).should().getRates()
    }

}