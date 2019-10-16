package com.dm.creativerun.domain.interactor.project

import com.dm.creativerun.domain.entity.Project
import com.dm.creativerun.domain.interactor.base.UseCase
import com.dm.creativerun.domain.repository.ProjectRepository

class GetProjectsUseCase(private val projectRepository: ProjectRepository) :
    UseCase<List<Project>, GetProjectsUseCase.Params>() {

    override suspend fun executeOnBackground(): List<Project> {
        return with(getParams()) {
            projectRepository.getProjects(page, perPage, query, sort, period, category, color)
        }
    }

    class Params(
        val page: Int,
        val perPage: Int,
        val query: String? = null,
        val sort: String? = null,
        val period: String? = null,
        val category: String? = null,
        val color: Int? = null
    )
}