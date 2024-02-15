package com.tareq.gittrack.ui.feature.search_screen

import com.tareq.gittrack.domain.model.GithubUser
import com.tareq.gittrack.domain.usecase.SearchGithubUsersUseCase
import com.tareq.gittrack.domain.util.ErrorHandler
import com.tareq.gittrack.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGithubUsersUseCase: SearchGithubUsersUseCase
) : BaseViewModel<SearchUiState, SearchUiEffect>(SearchUiState()) {

    suspend fun getGithubUsers(searchTerm: String) {
        if (searchTerm.isBlank()) {
            showToast("Search term should not be empty!")
        } else {
            _state.update {
                it.copy(
                    isLoading = true,
                    isError = false,
                    error = null,
                    searchPlaceholderVisibility = false,
                    isEmptySearchResult = false
                )
            }
            tryToExecute(
                { searchGithubUsersUseCase(searchTerm) },
                ::onGetGithubUsersSuccess,
                ::onGetGithubUsersError
            )
        }
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
        _state.update { it.copy(isLoading = false, isError = true, error = error) }
        when (error) {
            ErrorHandler.Forbidden -> showToast("Exceed the limit of search wait 1 hour please")
            ErrorHandler.InternalServer -> showToast("Server has a problem")
            ErrorHandler.InvalidData -> showToast("please search by valid github users name")
            ErrorHandler.InvalidInput -> showToast("please search by valid github users name")
            ErrorHandler.NoConnection -> showToast("no network connection")
            ErrorHandler.NotFound -> {
                _state.update { it.copy(isError = false, isEmptySearchResult = true) }
                showToast("users not found")
            }
        }
    }

    fun updateSearchTerm(searchTerm: String) {
        _state.update { it.copy(searchTerm = searchTerm) }
    }

    private fun showToast(message: String) {
        _state.update {
            it.copy(
                toast = state.value.toast.copy(
                    isShow = true,
                    message = message
                ),
                isLoading = false
            )
        }
        effectActionExecutor(_effect, SearchUiEffect.ShowToastEffect)
    }
}
