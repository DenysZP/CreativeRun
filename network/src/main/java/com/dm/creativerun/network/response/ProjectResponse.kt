package com.dm.creativerun.network.response

import com.dm.creativerun.network.response.data.ProjectDetailsData

class ProjectResponse internal constructor(private val project: ProjectDetailsData) {

    fun mapToEntity() = project.mapToEntity()
}