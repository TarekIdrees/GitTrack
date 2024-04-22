package com.tareq.gittrack.ui.feature.search_screen.composables

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.tareq.gittrack.R
import com.tareq.gittrack.ui.theme.GitTrackTheme

@Composable
fun GithubUserImage(
    modifier: Modifier = Modifier,
    imageUrl: String = ""
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(1f)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(imageUrl).crossfade(true)
                .build(),
            modifier = Modifier.fillMaxSize(),
            error = painterResource(id = R.drawable.image_error_placeholder),
            placeholder = painterResource(id = R.drawable.image_loading_placeholder),
            contentDescription = "github user image",
            contentScale = ContentScale.Crop,
            alignment = Alignment.TopStart,
        )
    }
}

@Preview
@Composable
private fun GithubUserImagePreview() {
    GitTrackTheme {
        GithubUserImage()
    }
}