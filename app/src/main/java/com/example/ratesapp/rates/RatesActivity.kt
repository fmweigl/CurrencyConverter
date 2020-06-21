package com.example.ratesapp.rates

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ratesapp.R
import com.example.ratesapp.databinding.ActivityRatesBinding
import com.example.ratesapp.attribution.AttributionActivity
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_rates, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_item_show_attribution) {
            showAttributionActivity()
            return true
        }

        return super.onOptionsItemSelected(item)
    }

    private fun showAttributionActivity() {
        startActivity(Intent(this, AttributionActivity::class.java))
    }
}