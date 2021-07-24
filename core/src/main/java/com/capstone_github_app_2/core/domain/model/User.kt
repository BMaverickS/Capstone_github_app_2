package com.capstone_github_app_2.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val login : String? = null,
    var avatarUrl : String? = null,
    var name : String? = null,
    var company : String? = null,
    var location : String? = null,
    var publicRepos : String? = null,
    var followers : String? = null,
    var following : String? = null,
    var favorite : Boolean? = null
) : Parcelable