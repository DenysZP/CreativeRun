package com.dm.creativerun.ui.category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.dm.creativerun.domain.entity.Category
import com.dm.creativerun.domain.interactor.category.GetCategoriesUseCase
import com.dm.creativerun.ui.base.BaseViewModel

class CategoryViewModel(private val getCategoriesUseCase: GetCategoriesUseCase) :
    BaseViewModel(getCategoriesUseCase) {

    private val _categoriesData = MutableLiveData<List<Category>>()

    val categoriesData: LiveData<List<Category>>
        get() = _categoriesData

    fun getCategories() {
        getCategoriesUseCase.execute {
            onSuccess { _categoriesData.value = it }
        }
    }
}