package com.example.ratesapp.rates

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ratesapp.R
import com.example.ratesapp.databinding.ActivityRatesBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class RatesActivity : AppCompatActivity() {

    private val viewModel: RatesViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityRatesBinding>(this, R.layout.activity_rates)
            .also {
                it.lifecycleOwner = this
                it.viewModel = viewModel
                it.rvRates.layoutManager = LinearLayoutManager(this)
                it.rvRates.adapter = RatesAdapter()
            }
    }
}