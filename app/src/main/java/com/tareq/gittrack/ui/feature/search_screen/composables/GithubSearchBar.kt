package com.tareq.gittrack.ui.feature.search_screen.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.tareq.gittrack.R
import com.tareq.gittrack.ui.theme.Brand
import com.tareq.gittrack.ui.theme.GitTrackTheme

@Composable
internal fun GithubSearchBar(
    modifier: Modifier = Modifier,
    searchTerm: String = "",
    onClickSearch: (String) -> Unit = {},
    onSearchTermUpdated: (String) -> Unit = {}
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    OutlinedTextField(
        value = searchTerm,
        onValueChange = onSearchTermUpdated,
        modifier = modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.background),
        label = {
            Text(
                stringResource(R.string.search),
                color = MaterialTheme.colorScheme.onSurface
            )
        },
        leadingIcon = {
            Icon(
                painterResource(id = R.drawable.ic_search),
                contentDescription = "search icon",
            )
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Search,
            autoCorrect = false,
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onClickSearch(searchTerm)
                keyboardController?.hide()
            }
        ),
        colors = TextFieldDefaults.colors(
            unfocusedLeadingIconColor = MaterialTheme.colorScheme.onSurface,
            focusedLeadingIconColor = Brand,

            unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
            focusedIndicatorColor = Brand,

            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedContainerColor = MaterialTheme.colorScheme.background,

            focusedPlaceholderColor = Brand,
            focusedLabelColor = Brand,
            focusedTextColor = MaterialTheme.colorScheme.onSurface,
            cursorColor = MaterialTheme.colorScheme.onSurface
        ),
    )
}

@Preview
@Composable
private fun GithubSearchBarPreview() {
    GitTrackTheme {
        GithubSearchBar()
    }
}