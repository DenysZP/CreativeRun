package com.dm.creativerun.ui.common.binding

import android.view.View
import androidx.databinding.BindingAdapter
import com.google.android.material.chip.Chip

@BindingAdapter("onCloseIconClick")
fun bindOnCloseIconClick(view: Chip, onCloseIconClickListener: View.OnClickListener?) {
    view.setOnCloseIconClickListener(onCloseIconClickListener)
}