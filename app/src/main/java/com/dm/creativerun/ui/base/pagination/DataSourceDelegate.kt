package com.dm.creativerun.ui.base.pagination

interface DataSourceDelegate<T> {

    fun requestPageData(page: Int, loadSize: Int, onResult: (List<T>) -> Unit)
}