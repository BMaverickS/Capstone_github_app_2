package com.capstone_github_app_2.core.data.source.remote.response

import com.google.gson.annotations.SerializedName

data class SearchListResponse(
    @field:SerializedName("items")
    var items : List<SearchResponse>
)
