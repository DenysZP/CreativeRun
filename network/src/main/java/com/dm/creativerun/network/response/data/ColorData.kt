package com.dm.creativerun.network.response.data

import android.graphics.Color

internal class ColorData(private val r: Int, private val g: Int, private val b: Int) {

    fun toIntColor() = Color.rgb(r, g, b)
}