package com.dm.creativerun.ui.view

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Color
import android.graphics.Outline
import android.util.AttributeSet
import android.view.View
import android.view.ViewOutlineProvider
import android.widget.FrameLayout
import com.dm.creativerun.R
import com.google.android.material.shape.MaterialShapeDrawable
import com.google.android.material.shape.RoundedCornerTreatment
import com.google.android.material.shape.ShapeAppearanceModel


class BackdropFrontLayer @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    private val backgroundDrawable: MaterialShapeDrawable?

    init {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.BackdropFrontLayer)
        val elevation =
            typedArray.getDimension(R.styleable.BackdropFrontLayer_android_elevation, 0f)
        val cornerRadius =
            typedArray.getDimension(R.styleable.BackdropFrontLayer_cornerRadius, 0f)
        val backgroundColor =
            typedArray.getColor(R.styleable.BackdropFrontLayer_backgroundColor, Color.WHITE)
        typedArray.recycle()

        val cornerTreatment = RoundedCornerTreatment(cornerRadius)
        val shapeAppearanceModel = ShapeAppearanceModel.builder()
            .setTopLeftCorner(cornerTreatment)
            .setTopRightCorner(cornerTreatment)
            .build()
        val backgroundDrawable = MaterialShapeDrawable(shapeAppearanceModel)
        backgroundDrawable.initializeElevationOverlay(context)
        backgroundDrawable.fillColor = ColorStateList.valueOf(backgroundColor)
        backgroundDrawable.elevation = elevation
        this.backgroundDrawable = backgroundDrawable
        background = backgroundDrawable
        clipToOutline = true
        outlineProvider = object : ViewOutlineProvider() {
            override fun getOutline(view: View, outline: Outline) {
                outline.setRoundRect(
                    0,
                    0,
                    view.width,
                    view.height + cornerRadius.toInt(),
                    cornerRadius
                )
            }
        }
    }

    override fun setElevation(elevation: Float) {
        super.setElevation(elevation)
        backgroundDrawable?.elevation = elevation
    }
}