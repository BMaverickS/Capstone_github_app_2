package com.capstone_github_app_2.core.data.source.local.room

import androidx.room.*
import com.capstone_github_app_2.core.data.source.local.entity.SearchEntity
import com.capstone_github_app_2.core.data.source.local.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {

    @Query("SELECT * FROM userList")
    fun getSearchDbData() : Flow<List<SearchEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSearch(search: List<SearchEntity>)

    @Update
    fun updateFavoriteSearch(search: SearchEntity)

    @Query("SELECT * FROM userDetails WHERE login = :username")
    fun getUserDbData(username : String) : Flow<UserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertUser(user: UserEntity)

    @Query("SELECT * FROM userDetails WHERE favorite = 1")
    fun getFavoriteUser() : Flow<List<UserEntity>>

    @Update
    fun updateFavoriteUSer(user: UserEntity)
}