package com.dm.creativerun.di

import com.dm.creativerun.ui.category.CategoryViewModel
import com.dm.creativerun.ui.details.DetailsViewModel
import com.dm.creativerun.ui.home.HomeViewModel
import com.dm.creativerun.ui.gallery.GallerySharedViewModel
import com.dm.creativerun.ui.search.SearchViewModel
import com.dm.creativerun.ui.splash.SplashViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { CategoryViewModel(get()) }
    viewModel { DetailsViewModel(get()) }
    viewModel { GallerySharedViewModel() }
}