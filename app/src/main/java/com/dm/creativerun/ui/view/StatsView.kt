package com.dm.creativerun.ui.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.databinding.BindingAdapter
import com.dm.creativerun.databinding.ViewStatsBinding
import com.dm.creativerun.domain.entity.Stats

class StatsView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : FrameLayout(context, attrs, defStyleAttr) {

    companion object {
        @BindingAdapter("stats")
        @JvmStatic
        fun bindStats(view: StatsView, stats: Stats?) {
            view.stats = stats
        }
    }

    var stats: Stats? = null
        set(value) {
            field = value
            binding.stats = value
        }

    private val binding: ViewStatsBinding =
        ViewStatsBinding.inflate(LayoutInflater.from(context), this, true)
}