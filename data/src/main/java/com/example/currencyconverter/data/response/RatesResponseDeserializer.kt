package com.example.currencyconverter.data.response

import com.google.gson.*
import java.lang.reflect.Type

private const val KEY_BASE_CURRENCY = "baseCurrency"
private const val KEY_RATES = "rates"

class RatesResponseDeserializer : JsonDeserializer<RatesResponse> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): RatesResponse {

        val jsonObject: JsonObject =
            json as? JsonObject ?: throw JsonParseException("Need a JsonElement")

        val baseCurrency = (jsonObject.get(KEY_BASE_CURRENCY) as JsonElement).asString

        val ratesObject = jsonObject.getAsJsonObject(KEY_RATES)

        val currencyRates = ratesObject.entrySet().map {
            RateResponse(currencyId = it.key, value = it.value.asBigDecimal)
        }

        return RatesResponse(baseCurrency, currencyRates)

    }
}