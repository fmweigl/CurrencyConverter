package com.example.ratesapp.attribution

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.example.ratesapp.R
import com.example.ratesapp.databinding.ActivityAttributionBinding

class AttributionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityAttributionBinding>(
            this,
            R.layout.activity_attribution
        )
            .also {
                it.tvFlagsText.movementMethod = LinkMovementMethod.getInstance()
                it.tvFlagsText.text =
                    Html.fromHtml(getString(R.string.attribution_text_country_flags))
            }
        title = getString(R.string.screen_title_attribution)
    }

}