package com.tareq.gittrack.data

import com.tareq.gittrack.data.api.model.GithubGeneralUserInformationResponse
import com.tareq.gittrack.data.api.model.GithubUserResponse
import com.tareq.gittrack.domain.model.GithubUser
import gittrack.githubuserdb.GithubUserEntity

object GithubUserDataSourceImpl : GithubUsersDataSource {
    override val githubUserName: String
        get() = "tareq"

    override fun getGithubUserResponse(): GithubUserResponse {
        return GithubUserResponse(
            avatarUrl = "avatar_url",
            bio = "bio",
            company = "company",
            createdAt = "2024-02-19",
            email = null,
            followers = 10,
            following = 20,
            id = 123,
            location = "location",
            userName = "tareq",
            name = "tareq",
            nodeId = "node_id",
            organizationsUrl = "organizations_url",
            publicGists = 5,
            publicRepos = 15,
            receivedEventsUrl = "received_events_url",
            reposUrl = "repos_url",
            siteAdmin = false,
            starredUrl = "starred_url",
            subscriptionsUrl = "subscriptions_url",
            type = "type",
            updatedAt = "2024-02-19",
            url = "url",
            twitterUsername = "twitterUsername",
            htmlUrl = "htmUrl",
            hireable = null,
            gravatarId = "1",
            gistsUrl = "",
            followingUrl = "followingUrl",
            followersUrl = "follwersUrl",
            eventsUrl = "eventsUrl",
            blog = null
        )
    }

    override fun getGithubUser(): GithubUser {
        return GithubUser(
            id = "123",
            userName = "tareq",
            bio = "bio",
            company = "company",
            avatarUrl = "avatar_url",
            createdAt = "2024-02-19",
            email = "",
            followers = "10",
            following = "20",
            location = "location",
            link = "https://github.com/tareq"
        )
    }

    override fun getGithubGeneralUserInformationResponse(): GithubGeneralUserInformationResponse {
        return GithubGeneralUserInformationResponse(
            avatarUrl = "avatar_url",
            eventsUrl = "events_url",
            followersUrl = "followers_url",
            followingUrl = "following_url",
            gistsUrl = "gists_url",
            gravatarId = "gravatar_id",
            htmlUrl = "html_url",
            id = 123,
            login = "tareq",
            nodeId = "node_id",
            organizationsUrl = "organizations_url",
            receivedEventsUrl = "received_events_url",
            reposUrl = "repos_url",
            score = 4.5,
            siteAdmin = false,
            starredUrl = "starred_url",
            subscriptionsUrl = "subscriptions_url",
            type = "user",
            url = "url"
        )
    }

    override fun getGithubUserEntity(): GithubUserEntity {
        return GithubUserEntity(
            github_user_id = 123L,
            name = "tareq",
            created_url = "",
            avatar_url = "",
            location = "",
            following = "",
            followers = "",
            email = "",
            company = "",
            bio = "",
            url = "https://github.com/tareq"
        )
    }

}