package com.tareq.gittrack.ui.common_composables

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tareq.gittrack.R
import com.tareq.gittrack.ui.theme.GitTrackTheme

@Composable
fun EmptySearchResultPlaceholder(state: Boolean) {
    AnimatedVisibility(
        visible = state,
        enter = fadeIn(
            animationSpec = tween(durationMillis = 500)
        ) + slideInVertically(),
        exit = fadeOut(
            animationSpec = tween(durationMillis = 500)
        ) + slideOutVertically()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = R.drawable.empty_search_placeholder),
                contentDescription = "empty search result placeholder",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 16.dp)
            )
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "There are no GitHub users with this name",
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Preview
@Composable
fun EmptySearchResultPlaceholderPrev() {
    GitTrackTheme {
        EmptySearchResultPlaceholder(state = true)
    }
}