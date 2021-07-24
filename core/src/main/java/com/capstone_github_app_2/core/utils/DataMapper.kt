package com.capstone_github_app_2.core.utils

import com.capstone_github_app_2.core.data.source.local.entity.SearchEntity
import com.capstone_github_app_2.core.data.source.local.entity.UserEntity
import com.capstone_github_app_2.core.data.source.remote.response.SearchResponse
import com.capstone_github_app_2.core.data.source.remote.response.UserResponse
import com.capstone_github_app_2.core.domain.model.Search
import com.capstone_github_app_2.core.domain.model.User

object DataMapper {

    fun mapResponsesToEntities(input: List<SearchResponse>): List<SearchEntity> {

        val searchListData = ArrayList<SearchEntity>()
        input.map {
            val srch = SearchEntity(login = it.login, avatarUrl = it.avatarUrl)

            searchListData.add(srch)
        }
        return searchListData
    }

    fun mapEntitiesToDomain(input: List<SearchEntity>): List<Search> =
        input.map {
            Search(login = it.login, avatarUrl = it.avatarUrl)
        }

    fun userMapResponsesToEntities(input: UserResponse?): UserEntity =

        input?.let {
            UserEntity(login = input.login, avatarUrl = input.avatarUrl, name = input.name,
                company = input.company, location = input.location, publicRepos = input.publicRepos,
                followers = input.followers, following = input.following)
        }!!

    fun userMapListEntitiesToDomain(input: List<UserEntity>?): List<User> =
        input?.map {
            User(login = it.login, avatarUrl = it.avatarUrl, name = it.name,
                company = it.company, location = it.location, publicRepos = it.publicRepos,
                followers = it.followers, following = it.following, favorite = it.favorite)
        }!!

    fun userMapEntitiesToDomain(input : UserEntity?) : User = User(
        login = input?.login, avatarUrl = input?.avatarUrl, name = input?.name,
        company = input?.company, location = input?.location, publicRepos = input?.publicRepos,
        followers = input?.followers, following = input?.following, favorite = input?.favorite
    )

    fun userMapDomainToEntity(input: User) = UserEntity(
        login = input.login!!, avatarUrl = input.avatarUrl!!, name = input.name!!,
        company = input.company!!, location = input.location!!, publicRepos = input.publicRepos!!,
        followers = input.followers!!, following = input.following!!, favorite = input.favorite!!
    )
}