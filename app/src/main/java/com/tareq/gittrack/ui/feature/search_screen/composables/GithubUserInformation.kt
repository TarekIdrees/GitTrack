package com.tareq.gittrack.ui.feature.search_screen.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tareq.gittrack.ui.theme.GitTrackTheme

@Composable
internal fun GithubUserInformation(
    modifier: Modifier = Modifier,
    githubUserName: String = "",
    githubUserBio: String = ""

) {
    var showFullCard by remember {
        mutableStateOf(false)
    }

    Column(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        Text(
            text = githubUserName, maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = modifier.height(8.dp))
        Text(
            modifier = Modifier.clickable { showFullCard = !showFullCard },
            text = githubUserBio,
            maxLines = if (showFullCard) 5 else 2,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(0.7f)
        )
    }
}

@Preview
@Composable
private fun GithubUserInformationPreview() {
    GitTrackTheme {
        GithubUserInformation()
    }
}