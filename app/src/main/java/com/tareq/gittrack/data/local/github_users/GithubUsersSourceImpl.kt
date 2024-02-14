package com.tareq.gittrack.data.local.github_users

import app.cash.sqldelight.coroutines.asFlow
import app.cash.sqldelight.coroutines.mapToList
import com.tareq.gittrack.GHUsersDatabase
import gittrack.githubuserdb.GetMatchedGithubUsersByName
import gittrack.githubuserdb.GithubUserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.transform

class GithubUsersSourceImpl(
    githubUsersDatabase: GHUsersDatabase
) : GithubUsersSource {

    private val queries = githubUsersDatabase.githubUsersEntityQueries
    override fun getMatchedUsersByName(name: String): Flow<List<GithubUserEntity>> {
        return queries.getMatchedGithubUsersByName(name).asFlow().mapToList(Dispatchers.IO)
            .transform { listOfUsersDatabase ->
                listOfUsersDatabase.forEach {
                    it.toGithubUserEntity()
                }
            }
    }

    private fun GetMatchedGithubUsersByName.toGithubUserEntity(): GithubUserEntity {
        return GithubUserEntity(
            github_user_id = github_user_id,
            name = name,
            bio = bio,
            company = company,
            avatar_url = avatar_url,
            created_url = created_url,
            email = email,
            followers = followers,
            following = following,
            location = location
        )
    }
}