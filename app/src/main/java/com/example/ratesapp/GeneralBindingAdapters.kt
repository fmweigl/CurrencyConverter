package com.example.ratesapp

import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.databinding.BindingAdapter

@BindingAdapter("bindDrawableResourceId")
fun ImageView.bindDrawableResourceId(@DrawableRes resId: Int?) {
    resId?.let {
        setImageResource(it)
    } ?: run {
        setImageDrawable(null)
    }
}