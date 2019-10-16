package com.dm.creativerun.ui.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import com.dm.creativerun.domain.entity.Project
import com.dm.creativerun.domain.entity.ProjectDetails
import com.dm.creativerun.domain.interactor.project.GetProjectUseCase
import com.dm.creativerun.ui.base.BaseViewModel

class DetailsViewModel(private val getProjectUseCase: GetProjectUseCase) :
    BaseViewModel(getProjectUseCase) {

    private val _projectData = MediatorLiveData<Project>()
    private val _projectDetailsData = MediatorLiveData<ProjectDetails>()

    val projectData: LiveData<Project>
        get() = _projectData
    val projectDetailsData: LiveData<ProjectDetails>
        get() = _projectDetailsData

    var args: DetailsFragmentArgs? = null
        set(value) {
            if (field == null) {
                value?.let {
                    field = it
                    val project = it.project
                    _projectData.value = project
                    getProjectDetails(project.id)
                }
            }
        }

    private fun getProjectDetails(projectId: Long) {
        getProjectUseCase.withParams(projectId)
            .execute {
                onSuccess { _projectDetailsData.value = it }
            }
    }
}