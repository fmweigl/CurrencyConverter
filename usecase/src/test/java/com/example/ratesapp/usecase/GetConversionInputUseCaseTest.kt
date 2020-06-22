package com.example.ratesapp.usecase

import com.example.ratesapp.data.repository.UserSelectionsRepository
import com.nhaarman.mockitokotlin2.given
import io.reactivex.rxjava3.core.Single
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class GetConversionInputUseCaseTest {

    @Mock
    private lateinit var repository: UserSelectionsRepository

    @InjectMocks
    private lateinit var tested: GetConversionInputUseCase

    @Before
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun `should get conversion input`() {
        val input = Optional.of("input")
        given(repository.getConversionInput()).willReturn(Single.just(input))

        tested.getUserConversionInput()
            .test()
            .assertNoErrors()
            .assertValue(input)
    }

}