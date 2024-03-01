package com.tareq.gittrack.ui.feature.search_screen


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.tareq.gittrack.domain.common.result.Result
import com.tareq.gittrack.domain.common.result.asResult
import com.tareq.gittrack.domain.usecase.SearchGithubUsersUseCase
import com.tareq.gittrack.ui.util.StringDictionary
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchGithubUsersUseCase: SearchGithubUsersUseCase,
    private val stringResources: StringDictionary
) : ViewModel() {

    private val _effect = MutableSharedFlow<SearchUiEffect>()
    val effect = _effect.asSharedFlow()

    private val _searchTermState = MutableStateFlow("")
    val searchTermState = _searchTermState.asStateFlow()

    private val _state: MutableStateFlow<SearchUiState> =
        MutableStateFlow(SearchUiState.SearchPlaceholder)
    val state = _state.asStateFlow()


    fun searchGithubUsers(searchTerm: String) {
        if (searchTerm.isBlank()) {
            val message = stringResources.emptySearchTerm
            showToast(message)
        } else {
            _state.value = SearchUiState.Loading // Update state to Loading before starting search
            viewModelScope.launch(Dispatchers.IO) {
                searchGithubUsersUseCase(searchTerm)
                    .asResult()
                    .map { result ->
                        when (result) {
                            Result.Loading -> SearchUiState.Loading

                            is Result.Success -> {
                                if (result.data.isEmpty()) {
                                    val message = stringResources.emptyResultString
                                    showToast(message)
                                }
                                SearchUiState.Success(
                                    githubUsers = result.data.map { it.toGithubUserUi() }
                                )
                            }

                            is Result.Error -> {
                                val errorMessage = stringResources.errorString.getOrDefault(
                                    result.error, ""
                                )
                                showToast(errorMessage)
                                SearchUiState.LoadFailed
                            }
                        }
                    }
                    .collect { newState ->
                        _state.value = newState // Update state with the new search result
                    }
            }
        }
    }

    fun updateSearchTerm(searchTerm: String) {
        _searchTermState.update { searchTerm }
    }

    private fun showToast(message: String) {
        effectActionExecutor(_effect, SearchUiEffect.ShowToastEffect(message))
    }

    fun openCardInBrowser(url: String) {
        if (url == "Undefined") {
            showToast("Undefined user link")
        } else {
            effectActionExecutor(_effect, SearchUiEffect.OpenCardInBrowserEffect(url))
        }
    }

    private fun effectActionExecutor(
        _effect: MutableSharedFlow<SearchUiEffect>,
        effect: SearchUiEffect,
    ) {
        viewModelScope.launch {
            _effect.emit(effect)
        }
    }
}