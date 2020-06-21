package com.example.ratesapp.rates

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.ratesapp.R
import com.example.ratesapp.databinding.ItemCurrencyBinding

class RatesAdapter : ListAdapter<CurrencyAdapterItem, CurrencyViewHolder>(DIFFUTIL_CALLBACK) {


    companion object {

        private val DIFFUTIL_CALLBACK = object : DiffUtil.ItemCallback<CurrencyAdapterItem>() {
            override fun areItemsTheSame(
                oldItem: CurrencyAdapterItem,
                newItem: CurrencyAdapterItem
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: CurrencyAdapterItem,
                newItem: CurrencyAdapterItem
            ): Boolean {
                return oldItem == newItem
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = DataBindingUtil.inflate<ItemCurrencyBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_currency,
            parent,
            false
        )
        return CurrencyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) =
        holder.bindCurrencyItem(getItem(position))

}

class CurrencyViewHolder(private val binding: ItemCurrencyBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bindCurrencyItem(item: CurrencyAdapterItem) {
        binding.tvCurrencyId.text = item.currencyId
        binding.tvCurrencyName.text = item.currencyName
        item.currencyFlagResId?.let {
            binding.ivCurrencyFlag.setImageResource(it)
        } ?: run {
            binding.ivCurrencyFlag.setImageDrawable(null)
        }

    }

}