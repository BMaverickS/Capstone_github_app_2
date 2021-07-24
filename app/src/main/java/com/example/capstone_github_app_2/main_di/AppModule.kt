package com.example.capstone_github_app_2.main_di

import com.capstone_github_app_2.core.domain.usecase.SearchInteractor
import com.capstone_github_app_2.core.domain.usecase.SearchUseCase
import com.example.capstone_github_app_2.details.DetailsViewModel
import com.example.capstone_github_app_2.main.SearchViewModel
import org.koin.android.viewmodel.dsl.viewModel
import org.koin.dsl.module

val searchUseCaseModule = module {
    factory<SearchUseCase> {
        SearchInteractor(get())
    }
}

val searchViewModelModule = module {
    viewModel {
        SearchViewModel(get())
    }

    viewModel {
        DetailsViewModel(get())
    }
}