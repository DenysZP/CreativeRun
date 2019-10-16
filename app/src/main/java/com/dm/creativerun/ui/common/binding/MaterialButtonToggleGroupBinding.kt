package com.dm.creativerun.ui.common.binding

import android.view.View
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import androidx.databinding.InverseBindingListener
import androidx.lifecycle.MutableLiveData
import com.google.android.material.button.MaterialButtonToggleGroup


@BindingAdapter(value = ["checkedIdDataAttrChanged"])
fun bindListener(view: MaterialButtonToggleGroup, listener: InverseBindingListener) {
    view.addOnButtonCheckedListener { group, checkedId, isChecked ->
        if (isChecked) {
            listener.onChange()
        }

        if (group.checkedButtonId == View.NO_ID && !isChecked) {
            group.check(checkedId)
        }
    }
}

@BindingAdapter("checkedIdData")
fun bindSetCheckedId(
    view: MaterialButtonToggleGroup,
    checkedIdData: MutableLiveData<Int>
) {
    checkedIdData.value?.let {
        if (view.checkedButtonId != it) {
            view.check(it)
        }
    }
}

@InverseBindingAdapter(attribute = "checkedIdData", event = "checkedIdDataAttrChanged")
fun bindGetCheckedId(view: MaterialButtonToggleGroup) = view.checkedButtonId