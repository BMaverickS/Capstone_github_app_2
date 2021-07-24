package com.capstone_github_app_2.favorite

import androidx.lifecycle.*
import com.capstone_github_app_2.core.domain.usecase.SearchUseCase

class FavoriteViewModel(searchUseCase : SearchUseCase) : ViewModel() {

    val favoriteUser = searchUseCase.getFavoriteUser().asLiveData()
}