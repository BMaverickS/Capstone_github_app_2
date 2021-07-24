package com.example.capstone_github_app_2

import android.app.Application
import com.capstone_github_app_2.core.di.searchDbModule
import com.capstone_github_app_2.core.di.searchNetworkModule
import com.capstone_github_app_2.core.di.searchRepoModule
import com.capstone_github_app_2.core.di.*
import com.example.capstone_github_app_2.main_di.searchUseCaseModule
import com.example.capstone_github_app_2.main_di.searchViewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    searchDbModule,
                    searchNetworkModule,
                    searchRepoModule,
                    searchUseCaseModule,
                    searchViewModelModule
                )
            )
        }
    }
}