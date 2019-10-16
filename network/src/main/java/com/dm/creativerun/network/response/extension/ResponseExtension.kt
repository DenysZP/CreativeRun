package com.dm.creativerun.network.response.extension

import com.dm.creativerun.network.response.Const.ORIGINAL_IMAGE_KEY

internal fun Map<String, String>.getSmallerImage() =
    minBy { it.key.toIntOrNull() ?: Int.MAX_VALUE }?.value

internal fun Map<String, String>.getBiggerImage(): String? {
    return if (this.containsKey(ORIGINAL_IMAGE_KEY)) {
        this[ORIGINAL_IMAGE_KEY]
    } else {
        maxBy { it.key.toIntOrNull() ?: Int.MIN_VALUE }?.value
    }
}