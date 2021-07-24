package com.capstone_github_app_2.core.data.source.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.capstone_github_app_2.core.data.source.local.entity.SearchEntity
import com.capstone_github_app_2.core.data.source.local.entity.UserEntity

@Database(entities = [SearchEntity::class, UserEntity::class], version = 1, exportSchema = false)
abstract class UserDatabase : RoomDatabase() {

    abstract fun UserDao() : UserDao
}