package com.seekho.animeviewer.view

import android.os.Build
import android.webkit.WebView
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.seekho.animeviewer.R
import com.seekho.animeviewer.component.BaseComponent
import com.seekho.animeviewer.model.AnimeDetailResponse
import com.seekho.animeviewer.state.TopAnimeState
import com.seekho.animeviewer.util.ThemePreview
import com.seekho.animeviewer.viewmodel.AnimeViewModel

@OptIn(ExperimentalMaterial3Api::class)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AnimeDetailScreen(navController: NavHostController, animeId: Int?) {

    val viewModel: AnimeViewModel = hiltViewModel()

    val animeid by remember { mutableStateOf(animeId) }
    var noDataFound by remember { mutableStateOf("") }

    var animeDetailData by remember { mutableStateOf<AnimeDetailResponse?>(null) }

    LaunchedEffect(animeid) {
        viewModel.animeDetails(animeid)
    }

    BaseComponent(viewModel = viewModel, stateObserver = { state ->
        when (state) {
            is TopAnimeState.TopAnimeDetailSuccessState -> {
                animeDetailData = state.data
                noDataFound = ""
                viewModel.dismissProgressBar()
            }

            is TopAnimeState.ShowMessage -> {
                noDataFound = state.msg
                viewModel.showToast(state.msg)
                viewModel.dismissProgressBar()
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
                            text = stringResource(R.string.detail_screen), fontSize = 20.sp, fontWeight = FontWeight.Bold
                        )
                    }, colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = Color(0xFFe146c6),
                        titleContentColor = Color.White,
                    )
                )
            }
        ) { paddingValues ->
            animeDetailData?.let { anime ->
                AnimeDetailContent(anime, paddingValues)
            } ?: Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = stringResource(R.string.loading),
                    color = Color.Gray,
                    fontSize = 18.sp
                )
            }
        }
    }
}

@Composable
fun AnimeDetailContent(animeData: AnimeDetailResponse, padding: PaddingValues) {
    val anime = animeData.data

    anime?.let {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {

            // Trailer video or poster image
            if (it.trailer?.youtube_id != null) {
                AndroidView(
                    factory = { context ->
                        WebView(context).apply {
                            settings.javaScriptEnabled = true
                            loadUrl("https://www.youtube.com/embed/${it.trailer.youtube_id}")
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            } else {
                Image(
                    painter = rememberAsyncImagePainter(it.images?.jpg?.image_url),
                    contentDescription = it.title ?: "Anime Poster",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .clip(RoundedCornerShape(10.dp))
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            it.title?.let { title ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF6200EE))) {
                            append(stringResource(R.string.title))
                        }
                        append(title)
                    },
                    fontSize = 22.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))


            it.synopsis?.let { synopsis ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF6200EE))) {
                            append(stringResource(R.string.plot))
                        }
                        append(synopsis)
                    },
                    fontSize = 16.sp
                )
            }


            Spacer(modifier = Modifier.height(8.dp))

            it.genres?.takeIf { it.isNotEmpty() }?.let { genres ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF6200EE))) {
                            append(stringResource(R.string.genres))
                        }
                        append(genres.joinToString(", ") { it.name ?: "" })
                    },
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            it.producers?.takeIf { it.isNotEmpty() }?.let { producers ->
                Text(
                    text = buildAnnotatedString {
                        withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF6200EE))) {
                            append(stringResource(R.string.main_cast))
                        }
                        append(producers.joinToString(", ") { it.name ?: "" })
                    },
                    fontSize = 14.sp
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF6200EE))) {
                        append(stringResource(R.string.number_of_episode))
                    }
                    append(it.episodes?.toString() ?: "N/A")
                },
                fontSize = 14.sp
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = buildAnnotatedString {
                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold, color = Color(0xFF6200EE))) {
                        append(stringResource(R.string.rating))
                    }
                    append(it.score?.toString() ?: "N/A")
                },
                fontSize = 14.sp
            )

        }
    } ?: Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        Text(text = stringResource(R.string.anime_data), color = Color.Gray, fontSize = 16.sp)
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
@ThemePreview
fun AnimeDetailPreview() {
    AnimeDetailScreen(
        navController = rememberNavController(),
        2
    )
}