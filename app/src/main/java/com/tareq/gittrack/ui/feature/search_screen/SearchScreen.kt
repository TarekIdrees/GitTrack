package com.tareq.gittrack.ui.feature.search_screen

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tareq.gittrack.ui.common_composables.ContentAnimation
import com.tareq.gittrack.ui.common_composables.Loading
import com.tareq.gittrack.ui.feature.search_screen.composables.GithubUserCard
import kotlinx.coroutines.flow.collectLatest
import android.widget.Toast
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.tareq.gittrack.ui.common_composables.ConnectionErrorPlaceholder
import com.tareq.gittrack.ui.common_composables.EmptySearchResultPlaceholder
import com.tareq.gittrack.ui.common_composables.SearchPlaceholder
import com.tareq.gittrack.ui.feature.search_screen.composables.GithubSearchBar
import com.tareq.gittrack.ui.theme.GitTrackTheme

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val searchTermState by viewModel.searchTermState.collectAsStateWithLifecycle()

    val context = LocalContext.current

    LaunchedEffect(key1 = viewModel.effect) {
        viewModel.effect.collectLatest {
            when (it) {
                is SearchUiEffect.ShowToastEffect -> {
                    Toast.makeText(
                        context,
                        it.message,
                        Toast.LENGTH_SHORT
                    ).show()
                }

                is SearchUiEffect.OpenCardInBrowserEffect -> {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(it.url))
                    context.startActivity(intent)
                }
            }
        }
    }

    SearchContent(
        searchGithubUsers = viewModel::searchGithubUsers,
        onSearchTermUpdated = viewModel::updateSearchTerm,
        onClickGithubUserCard = viewModel::openCardInBrowser,
        searchResult = state,
        searchTerm = searchTermState
    )
}


@Composable
internal fun SearchContent(
    searchGithubUsers: (String) -> Unit = {},
    onSearchTermUpdated: (String) -> Unit = {},
    onClickGithubUserCard: (String) -> Unit = {},
    searchResult: SearchUiState = SearchUiState.SearchPlaceholder,
    searchTerm: String = "",
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp)
    ) {
        GithubSearchBar(
            searchTerm = searchTerm,
            onClickSearch = searchGithubUsers,
            onSearchTermUpdated = onSearchTermUpdated
        )

        ContentAnimation(state = searchResult is SearchUiState.LoadFailed) {
            ConnectionErrorPlaceholder(
                onClickTryAgain = { searchGithubUsers(searchTerm) }
            )
        }

        ContentAnimation(state = searchResult is SearchUiState.Loading) {
            Loading()
        }

        ContentAnimation(state = searchResult is SearchUiState.SearchPlaceholder) {
            SearchPlaceholder()
        }

        ContentAnimation(state = searchResult is SearchUiState.Success) {
            if (searchResult is SearchUiState.Success && searchResult.isEmpty()) {
                EmptySearchResultPlaceholder()
            }
        }

        ContentAnimation(state = searchResult is SearchUiState.Success) {
            if (searchResult is SearchUiState.Success && !searchResult.isEmpty()) {
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(vertical = 16.dp)
                ) {
                    searchResult.githubUsers.forEach { githubUser ->
                        item {
                            GithubUserCard(
                                githubUser = githubUser,
                                onClickCard = { onClickGithubUserCard(githubUser.link) }
                            )
                        }
                    }
                }
            }
        }
    }
}


@Preview
@Composable
private fun SearchContentPreview() {
    GitTrackTheme {
        SearchContent()
    }
}