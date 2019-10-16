package com.dm.creativerun.ui.search

import android.content.Context
import androidx.appcompat.app.ActionBar
import androidx.vectordrawable.graphics.drawable.AnimatedVectorDrawableCompat
import com.dm.creativerun.R

class ActionBarIconAnimation(context: Context) {

    var actionBar: ActionBar? = null
    private val arrowBackToCloseDrawable =
        AnimatedVectorDrawableCompat.create(context, R.drawable.animation_arrow_back_to_close)
    private val closeToArrowBackDrawable =
        AnimatedVectorDrawableCompat.create(context, R.drawable.animation_close_to_arrow_back)

    fun animateToClose() {
        animate(arrowBackToCloseDrawable)
    }

    fun animateToArrowBack() {
        animate(closeToArrowBackDrawable)
    }

    private fun animate(drawable: AnimatedVectorDrawableCompat?) {
        actionBar?.let {
            it.setHomeAsUpIndicator(drawable)
            drawable?.start()
        }
    }
}