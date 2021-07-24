package com.capstone_github_app_2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchResponse(
    @field:SerializedName("login")
    val login : String,

    @field:SerializedName("avatar_url")
    val avatarUrl : String
)
