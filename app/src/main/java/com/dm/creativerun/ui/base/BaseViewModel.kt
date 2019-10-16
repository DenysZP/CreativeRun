package com.dm.creativerun.ui.base

import androidx.lifecycle.ViewModel
import com.dm.creativerun.domain.interactor.base.UseCase

open class BaseViewModel(private vararg val useCases: UseCase<*, *>) : ViewModel() {

    init {
        useCases.forEach { it.attachToLifecycle() }
    }

    override fun onCleared() {
        super.onCleared()
        useCases.forEach { it.cancel() }
    }
}