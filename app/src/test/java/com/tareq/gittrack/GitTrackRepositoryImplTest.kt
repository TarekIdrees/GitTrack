package com.tareq.gittrack

import com.tareq.gittrack.data.api.model.GithubGeneralUserInformationResponse
import com.tareq.gittrack.data.api.model.GithubSearchResponse
import com.tareq.gittrack.data.api.model.GithubUserResponse
import com.tareq.gittrack.data.api.repository.GitTrackRepositoryImpl
import com.tareq.gittrack.data.api.service.GitTrackApiService
import com.tareq.gittrack.data.api.util.NotFoundException
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Test

import org.junit.Assert.*
import retrofit2.Response

class GitTrackRepositoryImplTest {

    private val mockApiService: GitTrackApiService = mockk()
    private val repository = GitTrackRepositoryImpl(mockApiService)

    @Test
    fun `test searchGithubUser`() = runBlocking {
        // Test data
        val user = "tarek"
        // Mock the response from the service which is GithubUserResponse
        val response = mockk<GithubUserResponse>()

        // Mocking behavior of API service
        coEvery { mockApiService.searchGithubUser(user) } returns Response.success(response)

        // Invoking method under test
        val resultFlow = repository.searchGithubUser(user)
        resultFlow.collect { result ->
            assertTrue(result == response)
        }

        // Verifying method calls
        coVerify { mockApiService.searchGithubUser(user) }
    }

    @Test
    fun `test searchGithubUsers`() = runBlocking {
        // Test data
        val searchTerm = "tarek"
        // Mock the response from the service which is GithubSearchResponse
        val response = mockk<GithubSearchResponse>()
        // Mock the list of users that should be retrieved form the repository
        val usersList = mockk<List<GithubGeneralUserInformationResponse>>()

        // Mocking behavior of API service and response
        every { usersList.iterator() } returns emptyList<GithubGeneralUserInformationResponse>().iterator()
        every { response.githubUsers } returns usersList
        coEvery { mockApiService.searchGithubUsers(searchTerm) } returns Response.success(response)

        // Invoking method under test
        val resultFlow = repository.searchGithubUsers(searchTerm)
        resultFlow.collect { result ->
            assertEquals(result, usersList)
        }

        // Verifying method calls
        coVerify { mockApiService.searchGithubUsers(searchTerm) }
    }

    @Test(expected = NotFoundException::class)
    fun `test searchGithubUser with null response`() = runBlocking {
        // Test data
        val user = "tarek"

        // Mocking behavior of API service with null response
        coEvery { mockApiService.searchGithubUser(user) } returns Response.success(null)

        repository.searchGithubUser(user).collect { /* No need to collect anything */ }
    }

    @Test(expected = NotFoundException::class)
    fun `test searchGithubUsers with null response`() = runBlocking {
        // Test data
        val user = "tarek"

        // Mocking behavior of API service with null response
        coEvery { mockApiService.searchGithubUsers(user) } returns Response.success(null)

        // Invoking method under test
        repository.searchGithubUsers(user).collect { /* No need to collect anything */ }
    }

}