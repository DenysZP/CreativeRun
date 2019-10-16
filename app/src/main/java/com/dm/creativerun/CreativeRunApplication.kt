package com.dm.creativerun

import android.app.Application
import com.dm.creativerun.di.managerModule
import com.dm.creativerun.di.repositoryModule
import com.dm.creativerun.di.useCaseModule
import com.dm.creativerun.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class CreativeRunApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@CreativeRunApplication)
            modules(managerModule, repositoryModule, useCaseModule, viewModelModule)
        }
    }
}