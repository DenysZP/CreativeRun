package com.dm.creativerun.ui.common.binding

import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestListener

@BindingAdapter("imageFromUrl", "withCrossFade", "requestListener", requireAll = false)
fun bindImageFromUrl(
    view: ImageView,
    imageUrl: String?,
    withCrossFade: Boolean = true,
    requestListener: RequestListener<Drawable>?
) {
    if (!imageUrl.isNullOrEmpty()) {
        val transitionOptions = if (withCrossFade) {
            DrawableTransitionOptions().crossFade()
        } else {
            DrawableTransitionOptions()
        }
        val transition = Glide.with(view.context)
            .load(imageUrl)
            .transition(transitionOptions)

        requestListener?.let { transition.listener(it) }

        transition.into(view)
    }
}