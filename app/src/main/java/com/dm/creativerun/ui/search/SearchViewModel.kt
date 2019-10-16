package com.dm.creativerun.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dm.creativerun.R
import com.dm.creativerun.constant.Const
import com.dm.creativerun.domain.entity.Project
import com.dm.creativerun.domain.interactor.project.GetProjectsUseCase
import com.dm.creativerun.ui.base.BaseViewModel
import com.dm.creativerun.ui.base.pagination.DataSourceDelegate
import com.dm.creativerun.ui.base.pagination.SimpleDataSource
import com.dm.creativerun.ui.base.pagination.SimpleLivePagedListBuilder

class SearchViewModel(private val getProjectsUseCase: GetProjectsUseCase) :
    BaseViewModel(getProjectsUseCase) {

    private val dataSourceCreator: () -> SimpleDataSource<Project> = {
        SimpleDataSource(object :
            DataSourceDelegate<Project> {
            override fun requestPageData(
                page: Int, loadSize: Int, onResult: (List<Project>) -> Unit
            ) {
                if (isNotEmpty()) {
                    getProjectsUseCase.withParams(
                        GetProjectsUseCase.Params(
                            page = page,
                            perPage = loadSize,
                            query = query,
                            category = category,
                            color = color,
                            sort = sortKey,
                            period = periodKey
                        )
                    )
                        .execute {
                            onSuccess {
                                _emptyData.value = it.isEmpty() && page == 1
                                onResult(it)
                            }
                        }
                }
            }
        })
    }
    private val _emptyData = MutableLiveData(true)
    private var category: String? = null
    private var color: Int? = null
    private var sortKey: String? = Const.Sort.PUBLISHED_DATE
    private var periodKey: String? = Const.Period.ALL


    val projectsData = SimpleLivePagedListBuilder(dataSourceCreator).build()
    val colorData = MutableLiveData<Int>()
    val categoryData = MutableLiveData<String>()
    val sortData = MutableLiveData(R.id.dateButton)
    val periodData = MutableLiveData(R.id.allButton)
    val emptyData: LiveData<Boolean>
        get() = _emptyData

    var query: String? = null
        set(value) {
            if (field != value) {
                field = if (value?.isEmpty() == true) null else value
                invalidate()
            }
        }

    var args: SearchFragmentArgs? = null
        set(value) {
            field = value
            if (category == null) {
                category = value?.category
                categoryData.value = category
            }
            if (color == null) {
                color = value?.let { if (value.color == Int.MIN_VALUE) null else value.color }
                colorData.value = color
            }
        }

    fun setCategory(category: String) {
        if (categoryData.value != category) {
            categoryData.value = category
            refresh()
        }
    }

    fun refresh() {
        if (isNewData()) {
            sortKey = transformSort(sortData.value)
            periodKey = transformPeriod(periodData.value)
            category = categoryData.value
            color = colorData.value
            invalidate()
        }
    }

    fun clearColor() {
        colorData.value = null
    }

    fun clearCategory() {
        categoryData.value = null
    }

    private fun invalidate() {
        projectsData.value?.dataSource?.invalidate()
    }

    private fun isNewData(): Boolean {
        val isNewSortKey = sortKey != transformSort(sortData.value)
        val isNewPeriodKey = periodKey != transformPeriod(periodData.value)
        val isNewCategory = category != categoryData.value
        val isNewColor = color != colorData.value
        return isNewSortKey || isNewPeriodKey || isNewCategory || isNewColor
    }

    private fun isNotEmpty(): Boolean {
        return query != null || category != null || color != null && color != -1
    }

    private fun transformSort(id: Int?): String? {
        return when (id) {
            R.id.dateButton -> Const.Sort.PUBLISHED_DATE
            R.id.ratingButton -> Const.Sort.APPRECIATIONS
            R.id.commentsButton -> Const.Sort.COMMENTS
            R.id.viewsButton -> Const.Sort.VIEWS
            else -> null
        }
    }

    private fun transformPeriod(id: Int?): String? {
        return when (id) {
            R.id.allButton -> Const.Period.ALL
            R.id.dayButton -> Const.Period.TODAY
            R.id.weekButton -> Const.Period.WEEK
            R.id.monthButton -> Const.Period.MONTH
            else -> null
        }
    }
}