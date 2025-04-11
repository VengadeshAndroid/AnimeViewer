package com.seekho.animeviewer.view

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.seekho.animeviewer.R
import com.seekho.animeviewer.component.BaseComponent
import com.seekho.animeviewer.model.TopAnimeData
import com.seekho.animeviewer.state.TopAnimeState
import com.seekho.animeviewer.util.ThemePreview
import com.seekho.animeviewer.viewmodel.AnimeViewModel

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AnimeListScreen(navController: NavHostController) {

    val viewModel: AnimeViewModel = hiltViewModel()

    var topAnimeList by remember { mutableStateOf<List<TopAnimeData>>(emptyList()) }

    LaunchedEffect(key1 = Unit) {
        viewModel.showProgressBar()
        viewModel.topAnime()
    }


    BaseComponent(viewModel = viewModel, stateObserver = { state ->
        when (state) {
            is TopAnimeState.TopAnimeSuccessState -> {
                topAnimeList = state.data.data
                viewModel.dismissProgressBar()
            }

            is TopAnimeState.ShowMessage -> {
                viewModel.showToast(state.msg)
            }

            else -> {
                viewModel.dismissProgressBar()
            }
        }
    }) {

        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = stringResource(R.string.home_screen),
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFe146c6),
                        titleContentColor = Color.White,
                    )
                )
            }
        ) { innerPadding ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
                if (topAnimeList.isEmpty()) {
                    Text(
                        text = stringResource(R.string.loading),
                        modifier = Modifier.align(Alignment.Center),
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(8.dp)
                    ) {
                        items(topAnimeList) { anime ->
                            AnimeItem(navController, anime)
                            Spacer(modifier = Modifier.height(8.dp))
                        }
                    }
                }
            }
        }

    }

}

@Composable
fun AnimeItem(navController: NavHostController, anime: TopAnimeData) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(140.dp)
            .clickable {
                navController.navigate("detail/${anime.mal_id}")
            },
        elevation = CardDefaults.cardElevation(6.dp),
        shape = RoundedCornerShape(12.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)
        ) {

            Image(
                painter = rememberAsyncImagePainter(model = anime.images?.jpg?.image_url),
                contentDescription = anime.title,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .width(100.dp)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(10.dp))
            )

            Spacer(modifier = Modifier.width(12.dp))

            Column(
                verticalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(vertical = 4.dp)
            ) {
                anime.title?.let {
                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF6200EE))) {
                                append(stringResource(R.string.title))
                            }
                            append(it)
                        },
                        fontSize = 16.sp
                    )
                }

                Spacer(modifier = Modifier.height(2.dp))


                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF6200EE))) {
                            append(stringResource(R.string.number_of_episode))
                        }
                        append(anime.episodes?.toString() ?: "N/A")
                    },
                    fontSize = 14.sp
                )

                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF6200EE))) {
                            append(stringResource(R.string.rating))
                        }
                        append(anime.score.toString() ?: "N/A")
                    },
                    fontSize = 14.sp
                )
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
@ThemePreview
fun AnimeListPreview() {
    AnimeListScreen(navController = rememberNavController())
}