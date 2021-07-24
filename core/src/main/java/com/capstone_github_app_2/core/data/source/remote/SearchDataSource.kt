package com.capstone_github_app_2.core.data.source.remote

import android.util.Log
import com.capstone_github_app_2.core.data.source.remote.network.ApiResponse
import com.capstone_github_app_2.core.data.source.remote.network.ApiService
import com.capstone_github_app_2.core.data.source.remote.response.SearchResponse
import com.capstone_github_app_2.core.data.source.remote.response.UserResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class SearchDataSource(private val apiService : ApiService) {

    fun getAllSearch(usname : String) : Flow<ApiResponse<List<SearchResponse>>>
    {
        return flow {
            try {
                val response = apiService.getSearchListData(usname)
                val result = response.items

                if (result.isNotEmpty())
                {
                    emit(ApiResponse.Success(response.items))
                }
                else
                {
                    emit(ApiResponse.Empty)
                }
            }
            catch (e : Exception)
            {
                emit(ApiResponse.Error(e.toString()))
            }
        }.flowOn(Dispatchers.IO)
    }

    fun getUserDetails(usname : String) : Flow<ApiResponse<UserResponse>>
    {
        return flow {
            try {
                val response = apiService.getUserData(usname)

                if (response.login.isNotEmpty())
                {
                    val login : String?
                    val avatarUrl : String?
                    var name : String?
                    var company : String?
                    var location : String?
                    var publicRepos : String?
                    var followers : String?
                    var following : String?

                    login = response.login
                    avatarUrl = response.avatarUrl
                    name = response.name
                    company = response.company
                    location = response.location
                    publicRepos = response.publicRepos
                    followers = response.followers
                    following = response.following

                    name = if (name.isNullOrEmpty()) "No data"
                    else response.name

                    followers = if (followers.isNullOrEmpty()) "No data"
                    else response.followers

                    following = if (following.isNullOrEmpty()) "No data"
                    else response.following

                    company = if (company.isNullOrEmpty()) "No data"
                    else response.company

                    location = if (location.isNullOrEmpty()) "No data"
                    else response.location

                    publicRepos = if (publicRepos.isNullOrEmpty()) "No data"
                    else response.publicRepos

                    emit(ApiResponse.Success(UserResponse(login, avatarUrl, name, company,
                        location, publicRepos, followers, following)))
                }
                else
                {
                    emit(ApiResponse.Empty)
                }
            }
            catch (e : Exception)
            {
                emit(ApiResponse.Error(e.toString()))
                Log.e("SearchDataSource", e.toString())
            }
        }.flowOn(Dispatchers.IO)
    }
}