package com.example.ratesapp.rates

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.ratesapp.R
import com.example.ratesapp.databinding.ItemCurrencyBinding

class RatesAdapter(
    private val clickListener: IConversionClickedListener,
    private val inputListener: IConversionInputListener
) :
    RecyclerView.Adapter<CurrencyViewHolder>() {

    private val items = mutableListOf<CurrencyAdapterItem>()

    fun setItems(items: List<CurrencyAdapterItem>) {
        if (listsAreEqualIgnoringValue(this.items, items)) {
            updateOnlyValues(items)
            return
        }

        val diff = DiffUtil.calculateDiff(ItemsDiffUtilCallback(this.items, items))
        this.items.clear()
        this.items.addAll(items)
        diff.dispatchUpdatesTo(this)
    }

    private fun updateOnlyValues(newItems: List<CurrencyAdapterItem>) =
        this.items.forEachIndexed { index, currencyAdapterItem ->
            val newItem = newItems[index]
            if (currencyAdapterItem.value != newItem.value) {
                this.items[index] = newItem
                notifyItemChanged(index, newItem.value)
            }
        }

    fun getItem(index: Int): CurrencyAdapterItem? = items.getOrNull(index)

    override fun getItemCount(): Int = items.size

    private fun listsAreEqualIgnoringValue(
        oldList: List<CurrencyAdapterItem>,
        newList: List<CurrencyAdapterItem>
    ): Boolean {
        if (oldList.size != newList.size) {
            return false
        }

        oldList.forEachIndexed { index, currencyAdapterItem ->
            if (currencyAdapterItem.equalsIgnoringValue(newList[index]).not()) {
                return false
            }
        }
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrencyViewHolder {
        val binding = DataBindingUtil.inflate<ItemCurrencyBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_currency,
            parent,
            false
        )
        return CurrencyViewHolder(binding, inputListener, clickListener)
    }

    override fun onBindViewHolder(holder: CurrencyViewHolder, position: Int) =
        holder.bindCurrencyItem(items[position])

    override fun onBindViewHolder(
        holder: CurrencyViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        (payloads.firstOrNull() as? String)?.let { newValue ->
            holder.bindValue(newValue)
        } ?: run {
            super.onBindViewHolder(holder, position, payloads)
        }
    }
}

class CurrencyViewHolder(
    private val binding: ItemCurrencyBinding,
    private val inputListener: IConversionInputListener,
    clickListener: IConversionClickedListener
) : RecyclerView.ViewHolder(binding.root) {

    init {
        binding.clickListener = clickListener
        binding.etConversion.setOnClickListener {
            Log.d("yyy", "clicked")
        }
    }

    fun bindCurrencyItem(item: CurrencyAdapterItem) {
        binding.item = item
        if (item.isBase) {
            binding.inputListener = inputListener
            if (binding.etConversion.hasFocus().not()) {
                binding.etConversion.requestFocus()
            }
        } else {
            // don't need to listen to text changes on currencies other than the base currency
            binding.inputListener = null
        }
    }

    fun bindValue(value: String) {
        if (value != binding.etConversion.text?.toString()) {
            binding.etConversion.setText(value)
        }
    }
}

class ItemsDiffUtilCallback(
    private val oldItems: List<CurrencyAdapterItem>,
    private val newItems: List<CurrencyAdapterItem>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition].currencyId == newItems[newItemPosition].currencyId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldItems[oldItemPosition] == newItems[newItemPosition]
    }

    override fun getOldListSize(): Int {
        return oldItems.size
    }

    override fun getNewListSize(): Int {
        return newItems.size
    }
}