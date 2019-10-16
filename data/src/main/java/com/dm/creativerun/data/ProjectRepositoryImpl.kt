package com.dm.creativerun.data

import com.dm.creativerun.domain.entity.Project
import com.dm.creativerun.domain.entity.ProjectDetails
import com.dm.creativerun.domain.repository.ProjectRepository
import com.dm.creativerun.network.NetworkClient

class ProjectRepositoryImpl(
    private val networkClient: NetworkClient
) :
    ProjectRepository {

    override suspend fun getProjects(
        page: Int,
        perPage: Int,
        query: String?,
        sort: String?,
        period: String?,
        category: String?,
        color: Int?
    ): List<Project> {
        return networkClient.projectService.getProjectsAsync(
            page,
            perPage,
            query,
            sort,
            period,
            category,
            color?.let { Integer.toHexString(it).substring(2) }
        )
            .await()
            .mapToEntityList()
    }

    override suspend fun getProjectDetails(projectId: Long): ProjectDetails {
        return networkClient.projectService
            .getProjectDetailsByIdAsync(projectId)
            .await()
            .mapToEntity()
    }
}