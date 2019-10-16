package com.dm.creativerun.network.response.data

import com.dm.creativerun.domain.entity.ProjectDetails
import com.dm.creativerun.domain.entity.Stats
import com.dm.creativerun.network.response.Const.MILLISECONDS
import com.dm.creativerun.network.response.extension.getBiggerImage
import com.dm.creativerun.network.response.extension.getSmallerImage
import com.squareup.moshi.Json
import java.util.*

internal class ProjectDetailsData(
    private val id: Long,
    private val name: String,
    @field:Json(name = "created_on") private val createdOn: Long,
    private val url: String,
    private val fields: List<String>,
    private val covers: Map<String, String>,
    private val owners: List<UserData>,
    private val colors: List<ColorData>,
    private val stats: Stats,
    private val description: String,
    private val modules: List<ModuleData>
) {

    fun mapToEntity(): ProjectDetails {
        return ProjectDetails(
            id,
            name,
            Date(createdOn * MILLISECONDS),
            url,
            fields,
            covers.getBiggerImage(),
            covers.getSmallerImage(),
            owners.map { it.mapToEntity() },
            stats,
            colors.map { it.toIntColor() }.firstOrNull(),
            description,
            modules.mapNotNull { it.mapToEntity() }
        )
    }
}