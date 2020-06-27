package com.example.currencyconverter.framework.datastorage

import android.content.SharedPreferences
import com.example.currencyconverter.data.response.RatesResponse
import com.example.currencyconverter.framework.cache.RatesResponseCache
import com.google.gson.Gson
import com.nhaarman.mockitokotlin2.*
import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import java.util.*

class RatesResponseCacheTest {

    @Mock
    private lateinit var gson: Gson

    @Mock
    private lateinit var sharedPrefs: SharedPreferences

    @InjectMocks
    private lateinit var tested: RatesResponseCache

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        RxJavaPlugins.setIoSchedulerHandler { Schedulers.trampoline() }
    }

    @After
    fun tearDown() = RxJavaPlugins.setIoSchedulerHandler { null }

    @Test
    fun `should return cached response`() {
        val currencyId = "currencyId"
        val json = "json"
        val response: RatesResponse = mock()
        given(sharedPrefs.getString(eq(currencyId), anyOrNull())).willReturn(json)
        given(gson.fromJson(json, RatesResponse::class.java)).willReturn(response)

        tested.get(currencyId)
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue(Optional.of(response))
    }

    @Test
    fun `should return empty optional if no response was cached`() {
        given(sharedPrefs.getString(any(), anyOrNull())).willReturn(null)

        tested.get("currencyId")
            .test()
            .assertNoErrors()
            .assertComplete()
            .assertValue(Optional.empty())
    }


}