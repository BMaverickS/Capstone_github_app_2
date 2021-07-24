package com.capstone_github_app_2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @field:SerializedName("login")
    var login : String,

    @field:SerializedName("avatar_url")
    var avatarUrl : String,

    @field:SerializedName("name")
    var name : String,

    @field:SerializedName("company")
    var company : String,

    @field:SerializedName("location")
    var location : String,

    @field:SerializedName("public_repos")
    var publicRepos : String,

    @field:SerializedName("followers")
    var followers : String,

    @field:SerializedName("following")
    var following : String
)