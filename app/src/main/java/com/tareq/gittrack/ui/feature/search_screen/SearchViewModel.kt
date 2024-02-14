package com.tareq.gittrack.ui.feature.search_screen

import android.util.Log
import com.tareq.gittrack.domain.model.GithubUser
import com.tareq.gittrack.domain.usecase.SearchGithubUserUseCase
import com.tareq.gittrack.domain.usecase.SearchGithubUsersUseCase
import com.tareq.gittrack.domain.util.ErrorHandler
import com.tareq.gittrack.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGithubUserUseCase: SearchGithubUserUseCase,
    private val searchGithubUsersUseCase: SearchGithubUsersUseCase
) : BaseViewModel<SearchUiState, SearchUiEffect>(SearchUiState()) {
//    suspend fun getGithubUser(user: String) {
//        viewModelScope.launch {
//            getGithubUserUseCase(user).collect { gitHubUser ->
//                _state.update {
//                    it.copy(
//                        userName = gitHubUser.name!!,
//                        userBio = gitHubUser.bio!!
//                    )
//                }
//            }
//        }
//    }

    suspend fun getGithubUsers(searchTerm: String) {
        _state.update { it.copy(isLoading = true, searchTerm = searchTerm) }
        tryToExecute(
            { searchGithubUsersUseCase(searchTerm) },
            ::onGetGithubUsersSuccess,
            ::onGetGithubUsersError
        )
    }

    private fun onGetGithubUsersSuccess(githubUsers: List<GithubUser>) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                error = null,
                githubUsers = githubUsers.map { githubUser ->
                    githubUser.toGithubUserUi()
                }
            )
        }

    }

    private fun onGetGithubUsersError(error: ErrorHandler) {
        Log.d("Tarek", error.toString())
    }
}
