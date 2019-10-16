package com.dm.creativerun.network.response.data

import com.dm.creativerun.domain.entity.Image
import com.dm.creativerun.network.response.Const.IMAGE_TYPE
import com.dm.creativerun.network.response.Const.MAX_IMAGE_RATIO

internal class ModuleData(
    private val type: String,
    private val src: String,
    private val width: Int,
    private val height: Int
) {

    fun mapToEntity(): Image? {
        return if (type == IMAGE_TYPE && height / width <= MAX_IMAGE_RATIO) {
            Image(src, width, height)
        } else {
            null
        }
    }
}