package com.dm.creativerun.ui.search

import androidx.appcompat.widget.SearchView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

abstract class DebouncingQueryTextListener(
    private val lifecycleOwner: LifecycleOwner,
    private val timeMillis: Long
) :
    SearchView.OnQueryTextListener {

    private var debounceJob: Job? = null

    override fun onQueryTextSubmit(query: String?): Boolean {
        handleQuery(query, 0)
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        handleQuery(newText, timeMillis)
        return true
    }

    private fun handleQuery(query: String?, timeMillis: Long) {
        debounceJob?.cancel()
        debounceJob = lifecycleOwner.lifecycleScope.launch {
            query?.let {
                delay(timeMillis)
                onSearchQueryChange(it.trim())
            }
        }
    }

    abstract fun onSearchQueryChange(query: String)
}