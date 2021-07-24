package com.capstone_github_app_2.core.domain.usecase

import com.capstone_github_app_2.core.data.Resource
import com.capstone_github_app_2.core.domain.model.User
import com.capstone_github_app_2.core.domain.repository.ISearchRepository
import kotlinx.coroutines.flow.Flow

class SearchInteractor(private val searchRepository : ISearchRepository) : SearchUseCase {
    override fun getSearch(username : String) = searchRepository.getSearchData(username)

    override fun getUser(username : String) : Flow<Resource<User>> = searchRepository.getUserData(username)

    override fun getFavoriteUser() = searchRepository.getAllFavoriteUser()

    override fun setFavoriteUser(user : User, state : Boolean) = searchRepository.setAllFavoriteUser(user, state)
}