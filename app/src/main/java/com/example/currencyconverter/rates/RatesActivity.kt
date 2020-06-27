package com.example.currencyconverter.rates

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.currencyconverter.R
import com.example.currencyconverter.databinding.ActivityRatesBinding
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
                it.rvRates.adapter = RatesAdapter(viewModel, viewModel)
            }
    }

    override fun onResume() {
        super.onResume()
        viewModel.resumeUpdatingRates()
    }

    override fun onPause() {
        super.onPause()
        viewModel.pauseUpdatingRates()
    }
}