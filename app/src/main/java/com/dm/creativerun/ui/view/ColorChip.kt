package com.dm.creativerun.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import com.dm.creativerun.utils.convertToContrastColor
import com.google.android.material.chip.Chip


class ColorChip @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : Chip(context, attrs, defStyleAttr) {

    companion object {
        @BindingAdapter("color")
        @JvmStatic
        fun bindColors(
            view: ColorChip,
            color: Int?
        ) {
            view.setColor(color)
        }
    }

    private val defaultBackgroundColor = chipBackgroundColor
    private val defaultTextColor = textColors
    private val defaultCloseIconColor = closeIconTint
    private val defaultStrokeColor = ColorStateList.valueOf(Color.TRANSPARENT)

    fun setColor(color: Int?) {
        if (color != null) {
            chipBackgroundColor = ColorStateList.valueOf(color)
            convertToContrastColor(color)?.let {
                val contrastColor = ColorStateList.valueOf(it)
                setTextColor(contrastColor)
                closeIconTint = contrastColor
                chipStrokeColor = contrastColor
            }
        } else {
            chipBackgroundColor = defaultBackgroundColor
            setTextColor(defaultTextColor)
            chipIconTint = defaultCloseIconColor
            chipStrokeColor = defaultStrokeColor
        }
    }
}