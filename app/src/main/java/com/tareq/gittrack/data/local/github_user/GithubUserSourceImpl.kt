package com.tareq.gittrack.data.local.github_user


import com.tareq.gittrack.GHUsersDatabase
import gittrack.githubuserdb.GithubUserEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GithubUserSourceImpl @Inject constructor(
    githubUsersDatabase: GHUsersDatabase
) : GithubUserSource {

    private val queries = githubUsersDatabase.githubUserEntityQueries
    override suspend fun insertGithubUser(
        githubUser: GithubUserEntity
    ) {
        withContext(Dispatchers.IO) {
            queries.insertGithubUser(
                github_user_id = githubUser.github_user_id,
                name = githubUser.name,
                bio = githubUser.bio,
                company = githubUser.company,
                avatar_url = githubUser.avatar_url,
                created_url = githubUser.created_url,
                email = githubUser.email,
                followers = githubUser.followers,
                following = githubUser.following,
                location = githubUser.location,
                url = githubUser.url
            )
        }
    }

    override fun getMatchedUsersByName(name: String): Flow<List<GithubUserEntity>> {
        return flow {
            val query = queries.getMatchedGithubUsersByName(name)
            // Collect items emitted by the query into a list
            val userList = mutableListOf<GithubUserEntity>()
            query.executeAsList().forEach {
                userList.add(it)
            }
            if (userList.isEmpty()) {
                emptyFlow<List<GithubUserEntity>>()
            } else {
                emit(userList)
            }
        }.flowOn(Dispatchers.IO)
    }
}