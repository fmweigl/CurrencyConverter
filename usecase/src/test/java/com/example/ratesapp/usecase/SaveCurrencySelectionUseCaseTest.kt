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

class SaveCurrencySelectionUseCaseTest {

    @Mock
    private lateinit var repository: UserSelectionsRepository

    @InjectMocks
    private lateinit var tested: SaveCurrencySelectionUseCase

    @Before
    fun setUp() = MockitoAnnotations.initMocks(this)

    @Test
    fun `should save currency selection`() {
        val currency = "currency"
        given(repository.saveCurrencySelection(currency)).willReturn(Completable.complete())

        tested.saveUserCurrencySelection(currency)
            .test()
            .assertNoErrors()
            .assertComplete()

        then(repository).should().saveCurrencySelection(currency)
    }

}