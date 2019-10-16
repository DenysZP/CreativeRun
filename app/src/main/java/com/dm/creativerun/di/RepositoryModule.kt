package com.dm.creativerun.di

import com.dm.creativerun.data.CategoryRepositoryImpl
import com.dm.creativerun.data.ProjectRepositoryImpl
import com.dm.creativerun.database.AppDatabase
import com.dm.creativerun.domain.repository.CategoryRepository
import com.dm.creativerun.domain.repository.ProjectRepository
import com.dm.creativerun.network.NetworkClient
import org.koin.dsl.module

val repositoryModule = module {
    single { NetworkClient() }
    single { AppDatabase.getInstance(get()) }

    single<CategoryRepository> { CategoryRepositoryImpl(get(), get()) }
    single<ProjectRepository> { ProjectRepositoryImpl(get()) }
}