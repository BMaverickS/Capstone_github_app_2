package com.capstone_github_app_2.core.data.source.remote.network

import com.capstone_github_app_2.core.data.source.remote.response.SearchListResponse
import com.capstone_github_app_2.core.data.source.remote.response.UserResponse
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("search/users")
    suspend fun getSearchListData(@Query("q") username : String) : SearchListResponse

    @GET("users/{username}")
    suspend fun getUserData(@Path("username") username : String) : UserResponse
}
