package com.dm.creativerun.ui.extension

import android.view.View
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment

fun Fragment.waitForTransition(targetView: View) {
    postponeEnterTransition()
    targetView.doOnPreDraw { startPostponedEnterTransition() }
}