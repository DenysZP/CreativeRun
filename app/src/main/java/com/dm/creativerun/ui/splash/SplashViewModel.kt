package com.dm.creativerun.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dm.creativerun.domain.interactor.category.GetCategoriesUseCase
import com.dm.creativerun.ui.base.BaseViewModel

class SplashViewModel(private val getCategoriesUseCase: GetCategoriesUseCase) :
    BaseViewModel(getCategoriesUseCase) {

    private val _successEvent = MutableLiveData<Unit>()

    val successEvent: LiveData<Unit>
        get() = _successEvent

    fun fetchData() {
        getCategoriesUseCase.execute {
            onSuccess {
                _successEvent.value = Unit
            }
        }
    }
}