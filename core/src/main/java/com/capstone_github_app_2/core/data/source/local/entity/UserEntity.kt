package com.capstone_github_app_2.core.data.source.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "userDetails")
data class UserEntity(

    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "login")
    var login : String,

    @ColumnInfo(name = "avatarUrl")
    var avatarUrl : String,

    @ColumnInfo(name = "name")
    var name : String,

    @ColumnInfo(name = "company")
    var company : String,

    @ColumnInfo(name = "location")
    var location : String,

    @ColumnInfo(name = "publicRepos")
    var publicRepos : String,

    @ColumnInfo(name = "followers")
    var followers : String,

    @ColumnInfo(name = "following")
    var following : String,

    @ColumnInfo(name = "favorite")
    var favorite : Boolean = false
)
