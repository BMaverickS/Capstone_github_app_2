package com.capstone_github_app_2.core.data

import com.capstone_github_app_2.core.data.source.local.LocalDataSource
import com.capstone_github_app_2.core.data.source.remote.SearchDataSource
import com.capstone_github_app_2.core.data.source.remote.network.ApiResponse
import com.capstone_github_app_2.core.data.source.remote.response.SearchResponse
import com.capstone_github_app_2.core.data.source.remote.response.UserResponse
import com.capstone_github_app_2.core.domain.repository.ISearchRepository
import com.capstone_github_app_2.core.domain.model.Search
import com.capstone_github_app_2.core.domain.model.User
import com.capstone_github_app_2.core.utils.AppExecutors
import com.capstone_github_app_2.core.utils.DataMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SearchRepository(private val searchDataSource: SearchDataSource,
                       private val localDataSource: LocalDataSource,
                       private val appExecutors: AppExecutors) : ISearchRepository {

    override fun getSearchData(username: String): Flow<Resource<List<Search>>> {
        return object : NetworkBoundResource<List<Search>, List<SearchResponse>>() {
            override fun loadFromDB(): Flow<List<Search>> {
                return localDataSource.getSearchDbData().map {
                    DataMapper.mapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: List<Search>?): Boolean {
                return true
            }

            override suspend fun createCall(): Flow<ApiResponse<List<SearchResponse>>> {
                return searchDataSource.getAllSearch(username)
            }

            override suspend fun saveCallResult(data: List<SearchResponse>) {

                val srchList = DataMapper.mapResponsesToEntities(data)
                localDataSource.insertSearch(srchList)
            }
        }.asFlow()
    }

    override fun getUserData(username: String): Flow<Resource<User>> {
        return object : NetworkBoundResource<User, UserResponse>() {
            override fun loadFromDB(): Flow<User> {
                return localDataSource.getUserDbData(username).map {
                    DataMapper.userMapEntitiesToDomain(it)
                }
            }

            override fun shouldFetch(data: User?): Boolean {
                return data.let {
                    data?.login != username
                }
            }

            override suspend fun createCall(): Flow<ApiResponse<UserResponse>> {
                return searchDataSource.getUserDetails(username)
            }

            override suspend fun saveCallResult(data: UserResponse) {

                val usList = DataMapper.userMapResponsesToEntities(data)
                localDataSource.insertUser(usList)
            }
        }.asFlow()
    }

    override fun getAllFavoriteUser(): Flow<List<User>> {
        return localDataSource.getFavoriteUser().map {
            DataMapper.userMapListEntitiesToDomain(it)
        }
    }

    override fun setAllFavoriteUser(user: User, state: Boolean) {

        val userData = DataMapper.userMapDomainToEntity(user)
        appExecutors.diskIO().execute { localDataSource.setFavoriteUser(userData, state) }
    }
}