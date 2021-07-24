package com.example.capstone_github_app_2.details

import androidx.lifecycle.*
import com.capstone_github_app_2.core.domain.model.User
import com.capstone_github_app_2.core.domain.usecase.SearchUseCase

class DetailsViewModel(private val searchUseCase: SearchUseCase) : ViewModel() {

    fun setFavoriteUser(user : User, newStatus:Boolean) = searchUseCase.setFavoriteUser(user, newStatus)
}