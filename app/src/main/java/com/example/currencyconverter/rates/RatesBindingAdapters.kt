package com.example.currencyconverter.rates

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("bindCurrencyAdapterItems")
fun RecyclerView.bindCurrencyAdapterItems(items: List<CurrencyAdapterItem>?) {
    if (items == null) {
        return
    }
    (adapter as? RatesAdapter)?.let {
        val firstItemIsDifferentCurrency = items.firstOrNull() != it.getItem(0)
        it.setItems(items)
        if (firstItemIsDifferentCurrency) scrollToPosition(0)
    }

}