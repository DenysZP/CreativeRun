package com.dm.creativerun.ui.home

import com.dm.creativerun.constant.Const
import com.dm.creativerun.domain.entity.Project
import com.dm.creativerun.domain.interactor.project.GetProjectsUseCase
import com.dm.creativerun.ui.base.BaseViewModel
import com.dm.creativerun.ui.base.pagination.DataSourceDelegate
import com.dm.creativerun.ui.base.pagination.SimpleDataSource
import com.dm.creativerun.ui.base.pagination.SimpleLivePagedListBuilder

class HomeViewModel(private val getProjectsUseCase: GetProjectsUseCase) :
    BaseViewModel(getProjectsUseCase) {

    private val dataSourceCreator: () -> SimpleDataSource<Project> = {
        SimpleDataSource(object :
            DataSourceDelegate<Project> {
            override fun requestPageData(
                page: Int, loadSize: Int, onResult: (List<Project>) -> Unit
            ) {
                getProjectsUseCase.withParams(
                    GetProjectsUseCase.Params(
                        page = page,
                        perPage = loadSize,
                        sort = Const.Sort.VIEWS,
                        period = Const.Period.TODAY
                    )
                )
                    .execute {
                        onSuccess {
                            onResult(it)
                        }
                    }
            }
        })
    }

    val projectListData = SimpleLivePagedListBuilder(dataSourceCreator).build()
}