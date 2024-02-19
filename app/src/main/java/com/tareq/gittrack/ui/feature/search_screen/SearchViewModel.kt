package com.tareq.gittrack.ui.feature.search_screen

import androidx.lifecycle.viewModelScope
import com.tareq.gittrack.domain.model.GithubUser
import com.tareq.gittrack.domain.usecase.SearchGithubUsersOfflineUseCase
import com.tareq.gittrack.domain.usecase.SearchGithubUsersOnlineUseCase
import com.tareq.gittrack.domain.util.DatabaseErrorHandler
import com.tareq.gittrack.domain.util.ErrorHandler
import com.tareq.gittrack.domain.util.NetworkErrorHandler
import com.tareq.gittrack.ui.base.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGithubUsersOnlineUseCase: SearchGithubUsersOnlineUseCase,
    private val searchGithubUsersOfflineUseCase: SearchGithubUsersOfflineUseCase
) : BaseViewModel<SearchUiState, SearchUiEffect>(SearchUiState()) {


    //region offline
    private suspend fun getGithubUsersOffline(searchTerm: String) {
        tryToExecute(
            { searchGithubUsersOfflineUseCase(searchTerm) },
            ::onGetGithubUsersOfflineSuccess,
            ::onGetGithubUsersOfflineError
        )
    }

    private fun onGetGithubUsersOfflineSuccess(githubUsers: List<GithubUser>) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                error = null,
                githubUsers = githubUsers.map { githubUser ->
                    githubUser.toGithubUserUi()
                },
                screenContentVisibility = true
            )
        }
    }

    private fun onGetGithubUsersOfflineError(error: ErrorHandler) {
        _state.update { it.copy(isError = false, isEmptySearchResult = true) }
        when (error as DatabaseErrorHandler) {
            DatabaseErrorHandler.ForbiddenAndNotFound -> {
                showToast(" mo matched users offline and you exceed the limit of search wait 1 hour please")
            }

            DatabaseErrorHandler.NotFoundOffline -> {
                showToast("no matched users offline check your network and try again")
            }
        }
    }
    //endregion

    //region online
    suspend fun getGithubUsers(searchTerm: String) {
        if (searchTerm.isBlank()) {
            showToast("Search term should not be empty!")
        } else {
            _state.update {
                it.copy(
                    isLoading = true,
                    isError = false,
                    error = null,
                    searchTerm = searchTerm,
                    searchPlaceholderVisibility = false,
                    screenContentVisibility = false,
                    isEmptySearchResult = false
                )
            }
            tryToExecute(
                { searchGithubUsersOnlineUseCase(searchTerm) },
                ::onGetGithubUsersOnlineSuccess,
                ::onGetGithubUsersOnlineError
            )
        }
    }

    private fun onGetGithubUsersOnlineSuccess(githubUsers: List<GithubUser>) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = false,
                error = null,
                githubUsers = githubUsers.map { githubUser ->
                    githubUser.toGithubUserUi()
                },
                screenContentVisibility = true
            )
        }
    }

    private fun onGetGithubUsersOnlineError(error: ErrorHandler) {
        _state.update {
            it.copy(
                isLoading = false,
                isError = true,
                error = error,
                screenContentVisibility = false
            )
        }
        when (error as NetworkErrorHandler) {
            NetworkErrorHandler.Forbidden -> {
                showToast("Exceed the limit of search wait 1 hour please searching offline ....")
                viewModelScope.launch {
                    getGithubUsersOffline(state.value.searchTerm)
                }
            }

            NetworkErrorHandler.InternalServer -> showToast("Server has a problem")
            NetworkErrorHandler.InvalidData -> showToast("please search by valid github users name")
            NetworkErrorHandler.NoConnection -> {
                showToast("no network connection searching offline......")
                viewModelScope.launch {
                    getGithubUsersOffline(state.value.searchTerm)
                }
            }

            NetworkErrorHandler.NotFound -> {
                _state.update { it.copy(isError = false, isEmptySearchResult = true) }
                showToast("users not found")
            }
        }
    }
    //endregion

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

    fun openCardInBrowser(url: String) {
        if (url == "Undefined") {
            showToast("Undefined user link")
        } else {
            effectActionExecutor(_effect, SearchUiEffect.OpenCardInBrowser(url))
        }
    }
}
