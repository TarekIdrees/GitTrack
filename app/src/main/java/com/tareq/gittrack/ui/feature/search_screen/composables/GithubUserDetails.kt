package com.tareq.gittrack.ui.feature.search_screen.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tareq.gittrack.ui.theme.GitTrackTheme

@Composable
internal fun GithubUserDetails(
    modifier: Modifier = Modifier,
    label: String = "",
    information: String = ""
){
    Column(modifier = modifier) {
        Text(
            label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onTertiary
        )
        Spacer(modifier = Modifier.height(2.dp))
        Text(
            information,
            style = MaterialTheme.typography.labelMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Preview
@Composable
private fun GithubUserDetailsPreview() {
    GitTrackTheme {
        GithubUserDetails()
    }
}