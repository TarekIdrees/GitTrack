package com.tareq.gittrack.domain.usecase


import com.tareq.gittrack.domain.model.GithubUser
import com.tareq.gittrack.domain.util.DatabaseException
import com.tareq.gittrack.domain.util.ForbiddenAndNotFoundOffline
import com.tareq.gittrack.domain.util.ForbiddenException
import com.tareq.gittrack.domain.util.NoConnectionException
import kotlinx.coroutines.flow.Flow
import java.net.UnknownHostException
import javax.inject.Inject

class SearchManagementUseCase @Inject constructor(
    private val searchGithubUserUseCase: SearchGithubUserUseCase,
    private val insertGithubUserIntoDatabaseUseCase: InsertGithubUserIntoDatabaseUseCase,
    private val searchGithubUsersOfflineUseCase: SearchGithubUsersOfflineUseCase,
    private val searchGithubUsersOnlineUseCase: SearchGithubUsersOnlineUseCase
) {
    suspend fun searchGithubUser(userName: String): Flow<GithubUser> {
        val githubUser = searchGithubUserUseCase(userName)
        githubUser.collect { user ->
            insertGithubUserIntoDatabaseUseCase(user)
        }
        return githubUser
    }

    suspend fun searchGithubUsers(userName: String): Flow<List<GithubUser>> {
        return try {
            searchGithubUsersOnlineUseCase(userName)
        } catch (networkError: Exception) {
            if (networkError is NoConnectionException
                || networkError is ForbiddenException
                || networkError is UnknownHostException
            ) {
                try {
                    searchGithubUsersOfflineUseCase(userName)
                } catch (databaseError: DatabaseException) {
                    if (networkError is ForbiddenException) {
                        throw ForbiddenAndNotFoundOffline()
                    } else {
                        throw databaseError
                    }
                }
            } else
                throw networkError
        }
    }
}