package com.dm.creativerun.ui.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.dm.creativerun.domain.entity.User
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class UsersView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ChipGroup(context, attrs, defStyleAttr) {

    companion object {

        @BindingAdapter("users")
        @JvmStatic
        fun bindUsers(view: UsersView, users: List<User>?) {
            users?.let { view.users = it }
        }
    }

    var users: List<User> = listOf()
        set(value) {
            field = value
            addUsersView()
        }

    private fun addUsersView() {
        removeAllViews()
        users.forEach { createUserView(it) }
    }

    private fun createUserView(user: User) {
        val chip = Chip(context)
        chip.text = user.name
        addView(chip)
        Glide.with(chip)
            .asBitmap()
            .load(user.thumbnailUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(object : CustomTarget<Bitmap>() {
                override fun onLoadCleared(placeholder: Drawable?) {

                }

                override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                    chip.chipIcon = BitmapDrawable(resources, resource)
                }
            })
    }
}