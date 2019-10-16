package com.dm.creativerun.network.response.data

import com.dm.creativerun.domain.entity.Project
import com.dm.creativerun.domain.entity.Stats
import com.dm.creativerun.network.response.Const.MILLISECONDS
import com.dm.creativerun.network.response.extension.getBiggerImage
import com.dm.creativerun.network.response.extension.getSmallerImage
import com.squareup.moshi.Json
import java.util.*

internal class ProjectData(
    private val id: Long,
    private val name: String,
    @field:Json(name = "created_on") private val createdOn: Long,
    private val url: String,
    private val fields: List<String>,
    private val covers: Map<String, String>,
    private val colors: List<ColorData>,
    private val stats: Stats
) {

    fun mapToEntity(): Project {
        return Project(
            id,
            name,
            Date(createdOn * MILLISECONDS),
            url,
            fields,
            covers.getBiggerImage(),
            covers.getSmallerImage(),
            stats,
            colors.map { it.toIntColor() }
        )
    }
}