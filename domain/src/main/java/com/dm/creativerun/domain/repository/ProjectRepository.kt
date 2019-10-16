package com.dm.creativerun.domain.repository

import com.dm.creativerun.domain.entity.Project
import com.dm.creativerun.domain.entity.ProjectDetails

interface ProjectRepository {

    suspend fun getProjects(
        page: Int,
        perPage: Int,
        query: String? = null,
        sort: String? = null,
        period: String? = null,
        category: String? = null,
        color: Int? = null
    ): List<Project>

    suspend fun getProjectDetails(projectId: Long): ProjectDetails
}