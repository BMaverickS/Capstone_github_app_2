package com.capstone_github_app_2.core.data.source.local

import com.capstone_github_app_2.core.data.source.local.entity.SearchEntity
import com.capstone_github_app_2.core.data.source.local.entity.UserEntity
import com.capstone_github_app_2.core.data.source.local.room.UserDao
import kotlinx.coroutines.flow.Flow

class LocalDataSource(private val userDao: UserDao) {

    fun getSearchDbData() : Flow<List<SearchEntity>> = userDao.getSearchDbData()

    suspend fun insertSearch(searchList : List<SearchEntity>) = userDao.insertSearch(searchList)

    fun getUserDbData(username : String) : Flow<UserEntity> = userDao.getUserDbData(username)

    suspend fun insertUser(user : UserEntity) = userDao.insertUser(user)

    fun getFavoriteUser() : Flow<List<UserEntity>>
    {
        return userDao.getFavoriteUser()}

    fun setFavoriteUser(user : UserEntity, newState : Boolean)
    {
        user.favorite = newState
        userDao.updateFavoriteUSer(user)
    }
}