package com.example.ratesapp.base

import android.view.View
import android.widget.EditText
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

@BindingAdapter("bindEditTextText")
fun EditText.bindEditTextText(text: String?) {
    setText(text)
    setSelection(this.text.toString().length)
}

@BindingAdapter("bindVisibleOrGone")
fun View.bindVisibleOrGone(visible: Boolean) {
    visibility = if (visible) View.VISIBLE else View.GONE
}