package com.dm.creativerun.ui.base.pagination

import androidx.paging.PositionalDataSource

class SimpleDataSource<T>(
    private val dataSourceDelegate: DataSourceDelegate<T>
) : PositionalDataSource<T>() {

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<T>) {
        dataSourceDelegate.requestPageData(
            calculatePage(params.requestedStartPosition, params.requestedLoadSize),
            params.requestedLoadSize
        ) {
            callback.onResult(it, 0)
        }
    }

    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<T>) {
        dataSourceDelegate.requestPageData(
            calculatePage(params.startPosition, params.loadSize),
            params.loadSize
        ) {
            callback.onResult(it)
        }
    }

    private fun calculatePage(startPosition: Int, loadSize: Int) = (startPosition / loadSize) + 1
}