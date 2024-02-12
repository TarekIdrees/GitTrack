package com.tareq.gittrack.data.api.model

import com.google.gson.annotations.SerializedName


data class GithubSearchResponse(
    @SerializedName("incomplete_results")
    val incompleteResults: Boolean? = null,
    @SerializedName("items")
    val githubUsers: List<GithubGeneralUserInformationResponse?>? = null,
    @SerializedName("total_count")
    val totalCount: Int? = null
)
