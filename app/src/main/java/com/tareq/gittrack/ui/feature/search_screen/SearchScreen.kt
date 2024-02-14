package com.tareq.gittrack.ui.feature.search_screen

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.tareq.gittrack.ui.common_composables.ContentVisibility
import com.tareq.gittrack.ui.common_composables.Loading
import com.tareq.gittrack.ui.feature.search_screen.composables.GithubUserCard
import com.tareq.gittrack.ui.theme.Brand
import kotlinx.coroutines.launch

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    SearchContent(viewModel = viewModel, state = state)
}

@Composable
fun SearchContent(viewModel: SearchViewModel, state: SearchUiState) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val scope = rememberCoroutineScope()
    Loading(state = state.isLoading)
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp)
    ) {
        OutlinedTextField(
            value = state.searchTerm,
            onValueChange = {
                viewModel.updateSearchTerm(it)
            },
            modifier = Modifier
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.surface)
                .padding(top = 16.dp),
            label = { Text("Search") },
            leadingIcon = {
                Icon(imageVector = Icons.Default.Search, contentDescription = "search icon")
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Search,
                autoCorrect = false,
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    scope.launch {
                        viewModel.getGithubUsers(state.searchTerm)
                    }
                    keyboardController?.hide()
                }
            ),
            colors = TextFieldDefaults.colors(
                focusedLeadingIconColor = Brand,
                focusedPlaceholderColor = Brand,
                focusedLabelColor = Brand,
            ),
        )
        ContentVisibility(state = state.screenContentVisibility()) {
            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                state.githubUsers.forEach { githubUser ->
                    item {
                        GithubUserCard(githubUser = githubUser)
                    }
                }
            }
        }
    }


}