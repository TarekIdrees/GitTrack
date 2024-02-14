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
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import com.tareq.gittrack.ui.feature.search_screen.SearchUiState
import com.tareq.gittrack.ui.theme.Brand
import com.tareq.gittrack.ui.theme.GitTrackTheme

@Composable
fun ConnectionErrorPlaceholder(state: SearchUiState, onClickTryAgain: () -> Unit) {
    AnimatedVisibility(
        visible = state.isError,
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
                painter = painterResource(id = R.drawable.connection_error_placeholder),
                contentDescription = "connection error placeholder",
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .padding(bottom = 16.dp)
            )
            Text(
                modifier = Modifier.padding(bottom = 16.dp),
                text = "Opps, No connection!!",
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.colorScheme.onSurface,
                textAlign = TextAlign.Center,
            )
            Button(
                onClick = onClickTryAgain,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
                    .height(56.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Brand,
                    contentColor = MaterialTheme.colorScheme.onSurface,
                )
            ) {
                Loading(state = state.isLoading)
                AnimatedVisibility(
                    visible = !state.isLoading,
                    enter = fadeIn(animationSpec = tween(durationMillis = 500)),
                    exit = fadeOut(animationSpec = tween(durationMillis = 500))
                ) {
                    Row {
                        Text(
                            text = "Try again",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }
        }
    }
}

@Preview
@Composable
fun ConnectionErrorPlaceholderPreview() {
    GitTrackTheme {
        ConnectionErrorPlaceholder(state = SearchUiState(isError = true)) {

        }
    }
}