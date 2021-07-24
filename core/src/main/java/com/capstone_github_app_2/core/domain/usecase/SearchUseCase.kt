package com.capstone_github_app_2.core.domain.usecase

import com.capstone_github_app_2.core.data.Resource
import com.capstone_github_app_2.core.domain.model.Search
import com.capstone_github_app_2.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface SearchUseCase {
    fun getSearch(username : String) : Flow<Resource<List<Search>>>

    fun getUser(username : String) : Flow<Resource<User>>

    fun getFavoriteUser() : Flow<List<User>>

    fun setFavoriteUser(user : User, state : Boolean)
}