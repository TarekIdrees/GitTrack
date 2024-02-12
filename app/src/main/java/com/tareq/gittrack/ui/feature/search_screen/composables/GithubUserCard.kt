package com.tareq.gittrack.ui.feature.search_screen.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.tareq.gittrack.ui.feature.search_screen.GithubUserUi

@Composable
fun GithubUserCard(
    modifier: Modifier = Modifier,
    githubUser: GithubUserUi
) {
    Card(
        modifier = modifier,
        onClick = { /*TODO*/ }
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            Image(
                modifier = Modifier
                    .size(150.dp)
                    .clip(RoundedCornerShape(16.dp)),
                painter = rememberAsyncImagePainter(model = "https://media.istockphoto.com/id/1527223664/nl/foto/the-island-of-kalsoy-hiking-to-kallur-lighthouse-faroe-islands.jpg?s=612x612&w=0&k=20&c=TjL56NeFbEEP0UZHd80jkBJdW9nlpkyRRjuni5MMMIs="),
                contentDescription = "github user photo",
                contentScale = ContentScale.Crop
            )
            Column {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Column {
                        Text("name")
                        Text(githubUser.name)
                    }
                    Column {
                        Text("bio")
                        Text(githubUser.bio)
                    }
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("company")
                        Text(githubUser.company)
                    }
                    Column {
                        Text("createdAt")
                        Text(githubUser.createdAt)
                    }
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("email")
                        Text(githubUser.email)
                    }
                    Column {
                        Text("location")
                        Text(githubUser.location)
                    }
                }
                Row(horizontalArrangement = Arrangement.SpaceBetween) {
                    Column {
                        Text("following")
                        Text(githubUser.following)
                    }
                    Column {
                        Text("followers")
                        Text(githubUser.followers)
                    }
                }
            }
        }
    }
}