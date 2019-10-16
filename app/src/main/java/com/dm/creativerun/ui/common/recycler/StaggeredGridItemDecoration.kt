package com.dm.creativerun.ui.common.recycler

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

class StaggeredGridItemDecoration(private val space: Int) : RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {

        val spanCount = (parent.layoutManager as StaggeredGridLayoutManager).spanCount
        val spanIndex = (view.layoutParams as StaggeredGridLayoutManager.LayoutParams).spanIndex

        val position = parent.getChildAdapterPosition(view) + 1
        val halfSpace = space / 2
        val topSpace = if (position > spanCount) 0 else space

        when (spanIndex) {
            0 -> outRect.set(space, topSpace, halfSpace, space)
            else -> outRect.set(halfSpace, topSpace, space, space)
        }
    }
}