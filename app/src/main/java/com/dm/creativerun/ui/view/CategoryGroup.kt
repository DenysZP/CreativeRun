package com.dm.creativerun.ui.view

import android.content.Context
import android.util.AttributeSet
import androidx.databinding.BindingAdapter
import com.dm.creativerun.ui.common.recycler.OnCategoryClickListener
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup

class CategoryGroup @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : ChipGroup(context, attrs, defStyleAttr) {

    companion object {
        @BindingAdapter("categories", "onCategoryClick")
        @JvmStatic
        fun bindCategories(
            view: CategoryGroup,
            categories: List<String>?,
            clickListener: OnCategoryClickListener?
        ) {
            view.onCategoryClickListener = clickListener
            categories?.let { view.categories = it }
        }
    }

    var onCategoryClickListener: OnCategoryClickListener? = null
    var categories: List<String> = listOf()
        set(value) {
            field = value
            addChips()
        }

    private fun addChips() {
        removeAllViews()
        categories.forEach { category ->
            val chip = Chip(context)
            chip.text = category
            chip.setOnClickListener { onCategoryClickListener?.onCategoryClick(category) }
            addView(chip)
        }
    }
}