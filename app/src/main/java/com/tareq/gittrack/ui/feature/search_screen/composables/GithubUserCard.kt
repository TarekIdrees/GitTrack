package com.tareq.gittrack.ui.feature.search_screen.composables


import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.tareq.gittrack.R
import com.tareq.gittrack.ui.feature.search_screen.GithubUserUi
import com.tareq.gittrack.ui.theme.GitTrackTheme

@Composable
internal fun GithubUserCard(
    modifier: Modifier = Modifier,
    githubUser: GithubUserUi,
    onClickCard: (String) -> Unit = {},
) {
    Card(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.surface)
            .animateContentSize(),
        onClick = {
            onClickCard(githubUser.link)
        }
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                GithubUserImage(imageUrl = githubUser.avatarUrl)
                GithubUserInformation(
                    githubUserName = githubUser.name,
                    githubUserBio = githubUser.bio
                )
                Spacer(modifier = modifier.height(30.dp))
                Row(
                    modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        GithubUserDetails(label = stringResource(R.string.company), information = githubUser.company)
                        GithubUserDetails(label = stringResource(R.string.email), information = githubUser.email)
                        GithubUserDetails(label = stringResource(R.string.location), information = githubUser.location)

                    }
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                    ) {
                        GithubUserDetails(label = stringResource(R.string.createdat), information = githubUser.createdAt)
                        GithubUserDetails(label = stringResource(R.string.following), information = githubUser.following)
                        GithubUserDetails(label = stringResource(R.string.followers), information = githubUser.followers)
                    }
                }
            }
        }
    }
}

@Preview
@Composable
private fun GithubUserCardPreview() {
    GitTrackTheme {
        GithubUserCard(
            githubUser = GithubUserUi(
                name = "TarekIdrees",
                avatarUrl = "https://avatars.githubusercontent.com/u/58395863?v=4",
                bio = "Software Engineer worked at company size 10000000",
                company = "Tempo",
                email = "tarek.idrees.ad@gmail.com",
                createdAt = "2019-5-29",
                location = "Nederland",
                following = "100",
                followers = "100",
                link = "https://github.com/TarekIdrees"
            ),
            onClickCard = {}
        )
    }
}