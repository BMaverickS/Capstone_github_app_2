package com.capstone_github_app_2.core.domain.repository

import com.capstone_github_app_2.core.data.Resource
import com.capstone_github_app_2.core.domain.model.Search
import com.capstone_github_app_2.core.domain.model.User
import kotlinx.coroutines.flow.Flow

interface ISearchRepository {
    fun getSearchData(username : String) : Flow<Resource<List<Search>>>

    fun getUserData(username : String) : Flow<Resource<User>>

    fun getAllFavoriteUser(): Flow<List<User>>

    fun setAllFavoriteUser(user: User, state: Boolean)
}