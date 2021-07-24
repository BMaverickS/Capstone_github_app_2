package com.capstone_github_app_2.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userList")
data class SearchEntity(
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "login")
    var login : String,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl : String,
)
