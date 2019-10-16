package com.dm.creativerun.di

import com.dm.creativerun.manager.ImageDownloadManager
import com.dm.creativerun.manager.ThemePreferencesManager
import org.koin.dsl.module

val managerModule = module {
    single { ThemePreferencesManager(get()) }
    single { ImageDownloadManager(get()) }
}