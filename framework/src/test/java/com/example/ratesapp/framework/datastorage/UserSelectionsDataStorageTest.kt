package com.example.ratesapp.framework.datastorage

import android.content.SharedPreferences
import com.nhaarman.mockitokotlin2.*
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class UserSelectionsDataStorageTest {

    @Mock
    private lateinit var sharedPrefs: SharedPreferences

    @Mock
    private lateinit var editor: SharedPreferences.Editor

    @InjectMocks
    private lateinit var tested: UserSelectionsDataStorage

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        given(sharedPrefs.edit()).willReturn(editor)
        given(editor.putString(any(), any())).willReturn(editor)
    }

    @Test
    fun `should save currency selection`() {
        val currency = "currency"

        tested.saveCurrencySelection(currency)
            .test()
            .assertNoErrors()
            .assertComplete()

        then(editor).should().putString("KEY_CURRENCY_SELECTION", currency)
        then(editor).should().apply()
    }

    @Test
    fun `should save conversion input`() {
        val input = "input"

        tested.saveConversionInput(input)
            .test()
            .assertNoErrors()
            .assertComplete()

        then(editor).should().putString("KEY_CONVERSION_INPUT", input)
        then(editor).should().apply()
    }

    @Test
    fun `should return empty optional if no currency saved`() {
        given(sharedPrefs.getString(eq("KEY_CURRENCY_SELECTION"), anyOrNull()))
            .willReturn(null)

        tested.getCurrencySelection()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue(Optional.empty())
    }

    @Test
    fun `should return saved currency`() {
        val currency = "currency"
        given(sharedPrefs.getString(eq("KEY_CURRENCY_SELECTION"), anyOrNull()))
            .willReturn(currency)

        tested.getCurrencySelection()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue(Optional.of(currency))
    }

    @Test
    fun `should return empty optional if no conversion input saved`() {
        given(sharedPrefs.getString(eq("KEY_CONVERSION_INPUT"), anyOrNull()))
            .willReturn(null)

        tested.getConversionInput()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue(Optional.empty())
    }

    @Test
    fun `should return saved conversion input`() {
        val input = "input"
        given(sharedPrefs.getString(eq("KEY_CONVERSION_INPUT"), anyOrNull()))
            .willReturn(input)

        tested.getConversionInput()
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue(Optional.of(input))
    }
}