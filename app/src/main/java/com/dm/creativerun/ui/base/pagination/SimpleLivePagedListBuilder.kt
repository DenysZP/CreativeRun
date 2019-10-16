package com.dm.creativerun.ui.base.pagination

import androidx.paging.DataSource
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.dm.creativerun.constant.Const.Pagination.PER_PAGE

class SimpleLivePagedListBuilder<Key, Value>(private val dataSourceCreator: () -> DataSource<Key, Value>) {

    private val factory = object : DataSource.Factory<Key, Value>() {
        override fun create(): DataSource<Key, Value> {
            return dataSourceCreator()
        }
    }

    private val config = PagedList.Config
        .Builder()
        .setEnablePlaceholders(false)
        .setInitialLoadSizeHint(PER_PAGE)
        .setPageSize(PER_PAGE)
        .build()

    fun build() = LivePagedListBuilder(factory, config).build()
}