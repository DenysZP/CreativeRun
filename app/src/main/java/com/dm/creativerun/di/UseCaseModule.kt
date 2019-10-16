package com.dm.creativerun.di

import com.dm.creativerun.domain.interactor.category.GetCategoriesUseCase
import com.dm.creativerun.domain.interactor.project.GetProjectUseCase
import com.dm.creativerun.domain.interactor.project.GetProjectsUseCase
import org.koin.dsl.module

val useCaseModule = module {
    factory { GetCategoriesUseCase(get()) }
    factory { GetProjectsUseCase(get()) }
    factory { GetProjectUseCase(get()) }
}