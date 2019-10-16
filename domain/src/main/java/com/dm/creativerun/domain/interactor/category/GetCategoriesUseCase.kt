package com.dm.creativerun.domain.interactor.category

import com.dm.creativerun.domain.entity.Category
import com.dm.creativerun.domain.interactor.base.UseCase
import com.dm.creativerun.domain.repository.CategoryRepository

class GetCategoriesUseCase(private val categoryRepository: CategoryRepository) :
    UseCase<List<Category>, Unit>() {

    override suspend fun executeOnBackground() = categoryRepository.getCategories()
}