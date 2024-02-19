package com.tareq.gittrack.data

import com.tareq.gittrack.data.api.model.GithubGeneralUserInformationResponse
import com.tareq.gittrack.data.api.model.GithubUserResponse
import com.tareq.gittrack.domain.model.GithubUser
import gittrack.githubuserdb.GithubUserEntity

interface GithubUsersDataSource {

    val githubUserName: String
    fun getGithubUserResponse(): GithubUserResponse

    fun getGithubUser(): GithubUser

    fun getGithubGeneralUserInformationResponse(): GithubGeneralUserInformationResponse

    fun getGithubUserEntity(): GithubUserEntity
}