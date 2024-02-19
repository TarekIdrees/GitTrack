package com.tareq.gittrack

import com.tareq.gittrack.data.GithubUserDataSourceImpl
import com.tareq.gittrack.data.api.model.GithubGeneralUserInformationResponse
import com.tareq.gittrack.data.api.model.GithubSearchResponse
import com.tareq.gittrack.data.api.model.GithubUserResponse
import com.tareq.gittrack.data.repository.GitTrackRepositoryImpl
import com.tareq.gittrack.data.api.service.GitTrackApiService
import com.tareq.gittrack.data.local.github_user.GithubUserSourceImpl
import com.tareq.gittrack.domain.model.GithubUser
import com.tareq.gittrack.domain.util.NotFoundException
import gittrack.githubuserdb.GithubUserEntity
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import org.junit.Before
import retrofit2.Response


internal class GitTrackRepositoryImplTest {

    private val mockApiService: GitTrackApiService = mockk()
    private val mockUserTableMethods: GithubUserSourceImpl = mockk()
    private val repository = GitTrackRepositoryImpl(
        githubUserSourceImpl = mockUserTableMethods,
        gitTrackApiService = mockApiService
    )

    private lateinit var githubUserResponse: GithubUserResponse
    private lateinit var githubUser: GithubUser
    private lateinit var githubGeneralUserInformationResponse: GithubGeneralUserInformationResponse
    private lateinit var githubUserEntity: GithubUserEntity
    private lateinit var githubUserName: String

    @Before
    fun setup() {
        githubUserName = GithubUserDataSourceImpl.githubUserName
        githubUserResponse = GithubUserDataSourceImpl.getGithubUserResponse()
        githubUser = GithubUserDataSourceImpl.getGithubUser()
        githubGeneralUserInformationResponse =
            GithubUserDataSourceImpl.getGithubGeneralUserInformationResponse()
        githubUserEntity = GithubUserDataSourceImpl.getGithubUserEntity()
    }

    @Test
    fun `test searchGithubUser`() = runBlocking {
        // Mock data
        val user = githubUserName

        // Mock the response from the service
        coEvery { mockApiService.searchGithubUser(user) } returns Response.success(
            githubUserResponse
        )

        // Invoke the method under test
        val resultFlow = repository.searchGithubUser(user)

        // Collect the result
        val result = mutableListOf<GithubUser>()
        resultFlow.collect { itemUser ->
            result.add(itemUser)
        }

        // Verify
        assertEquals(githubUser, result.first())
    }

    @Test(expected = NotFoundException::class)
    fun `test searchGithubUser with null response`() = runBlocking {
        // Mock data
        val user = "fdghtrhsrsbdffsf"

        // Mock the response from the service
        coEvery { mockApiService.searchGithubUser(user) } returns Response.success(null)

        // Invoke the method under test
        repository.searchGithubUser(user).collect {/* No need to collect anything */ }
    }

    @Test
    fun `test searchGithubUsers`() = runBlocking {
        // Mock data
        val user = githubUserName
        val githubUsers = List(3) { githubUser }

        // Mock the response from the service
        coEvery { mockApiService.searchGithubUser(user) } returns Response.success(
            githubUserResponse
        )

        // Mock the response from the service
        coEvery { mockApiService.searchGithubUsers(user) } returns Response.success(
            GithubSearchResponse(
                githubUsers = List(3) { githubGeneralUserInformationResponse },
                totalCount = 3,
                incompleteResults = false,
            )
        )


        // Invoke the method under test
        val resultFlow = repository.searchGithubUsers(user)

        // Collect the result
        var result = mutableListOf<GithubUser>()
        resultFlow.collect { users ->
            result = users.toMutableList()
        }

        // Verify
        assertEquals(3, result.size)
        assertEquals(githubUsers, result)
    }

    @Test(expected = NotFoundException::class)
    fun `test searchGithubUsers with null response`() = runBlocking {
        // Test data
        val user = "ksdjfnksnfdfls"

        // Mocking behavior of API service with null response
        coEvery { mockApiService.searchGithubUsers(user) } returns Response.success(null)

        repository.searchGithubUsers(user).collect { /* No need to collect anything */ }
    }
}
