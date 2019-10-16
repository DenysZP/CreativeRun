package com.dm.creativerun.ui.common.binding

import androidx.databinding.BindingConversion

@BindingConversion
fun convertColorToHexString(intColor: Int?) =
    intColor?.let { String.format("#%06X", (0xFFFFFF and intColor)) } ?: ""
