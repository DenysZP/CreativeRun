package com.dm.creativerun.network.response

import com.dm.creativerun.network.response.data.ProjectData

class ProjectsResponse internal constructor(private val projects: List<ProjectData>) {

    fun mapToEntityList() = projects.map { it.mapToEntity() }
}