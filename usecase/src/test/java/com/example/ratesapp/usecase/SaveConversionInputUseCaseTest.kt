package com.example.ratesapp.usecase

import com.example.ratesapp.data.repository.UserSelectionsRepository
import com.nhaarman.mockitokotlin2.given
import com.nhaarman.mockitokotlin2.then
import io.reactivex.rxjava3.core.Completable
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations

class SaveConversionInputUseCaseTest {

    @Mock
    private lateinit var repository: UserSelectionsRepository

    @InjectMocks
    private lateinit var tested: SaveConversionInputUseCase

    @Before
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun `should save conversion input`() {
        val input = "input"
        given(repository.saveConversionInput(input)).willReturn(Completable.complete())

        tested.saveConversionInput(input)
            .test()
            .assertNoErrors()
            .assertComplete()

        then(repository).should().saveConversionInput(input)
    }
}