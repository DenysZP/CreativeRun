package com.dm.creativerun.ui.common.binding

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.databinding.BindingAdapter

@BindingAdapter("isGone")
fun bindIsGone(view: View, isGone: Boolean) {
    view.visibility = if (isGone) GONE else VISIBLE
}
