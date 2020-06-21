package com.example.ratesapp.rates

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

@BindingAdapter("bindCurrencyAdapterItems")
fun RecyclerView.bindCurrencyAdapterItems(items: List<CurrencyAdapterItem>?) {
    items?.let {
        (adapter as? RatesAdapter)?.submitList(it)
    }

}