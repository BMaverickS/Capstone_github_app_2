package com.example.capstone_github_app_2.main

import androidx.lifecycle.*
import com.capstone_github_app_2.core.data.Resource
import com.capstone_github_app_2.core.domain.model.Search
import com.capstone_github_app_2.core.domain.model.User
import com.capstone_github_app_2.core.domain.usecase.SearchUseCase

class SearchViewModel(private val searchUseCase: SearchUseCase) : ViewModel() {

    private var usname = MutableLiveData<String>()
    private var username = MutableLiveData<String>()

    fun setUsname(usname : String)
    {
        this.usname.value = usname
    }

    fun setUsername(username : String)
    {
        this.username.value = username
    }

    var search : LiveData<Resource<List<Search>>> = Transformations.switchMap(usname) { usname ->
        searchUseCase.getSearch(usname).asLiveData()
    }

    var user : LiveData<Resource<User>> = Transformations.switchMap(username) { username ->
        searchUseCase.getUser(username).asLiveData()
    }
}