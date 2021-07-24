package com.capstone_github_app_2.core.data.source.remote.network

sealed class ApiResponse<out R> {

    data class Success<out T>(val data : T) : ApiResponse<T>()

    data class Error(val errorMessage : String) : ApiResponse<Nothing>()

    object Empty : ApiResponse<Nothing>()
}