package com.dm.creativerun.utils

import android.graphics.Color
import androidx.core.graphics.ColorUtils

private const val WHITE_COLOR_BOUND_RATIO = 7

fun convertToContrastColor(intColor: Int?): Int? {
    return intColor?.let {
        val ratio = ColorUtils.calculateContrast(it, Color.BLACK)
        return if (ratio > WHITE_COLOR_BOUND_RATIO) Color.BLACK else Color.WHITE
    }
}