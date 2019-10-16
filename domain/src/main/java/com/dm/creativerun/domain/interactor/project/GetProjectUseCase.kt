package com.dm.creativerun.domain.interactor.project

import com.dm.creativerun.domain.entity.ProjectDetails
import com.dm.creativerun.domain.interactor.base.UseCase
import com.dm.creativerun.domain.repository.ProjectRepository

class GetProjectUseCase(private val projectRepository: ProjectRepository) :
    UseCase<ProjectDetails, Long>() {

    override suspend fun executeOnBackground() = projectRepository.getProjectDetails(getParams())
}