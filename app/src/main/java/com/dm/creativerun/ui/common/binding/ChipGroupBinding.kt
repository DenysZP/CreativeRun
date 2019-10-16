package com.dm.creativerun.ui.common.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import com.google.android.material.chip.ChipGroup


@BindingAdapter(value = ["checkedChipIdDataAttrChanged"])
fun bindListener(view: ChipGroup, listener: InverseBindingListener) {
    view.setOnCheckedChangeListener { group, checkedId ->
        val isChecked = group.checkedChipId != View.NO_ID
        if (isChecked) {
            view.tag = checkedId
            listener.onChange()
        }

        if (group.checkedChipId == View.NO_ID && !isChecked) {
            group.check(view.tag as? Int ?: View.NO_ID)
        }
    }
}

@BindingAdapter("checkedChipIdData")
fun bindSetCheckedId(
    view: ChipGroup,
    checkedIdData: MutableLiveData<Int>
) {
    checkedIdData.value?.let {
        if (view.checkedChipId != it) {
            view.check(it)
        }
    }
}

@InverseBindingAdapter(attribute = "checkedChipIdData", event = "checkedChipIdDataAttrChanged")
fun bindGetCheckedId(view: ChipGroup) = view.checkedChipId