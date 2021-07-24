package com.capstone_github_app_2.core.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Search(
    val login : String,
    val avatarUrl : String
) : Parcelable