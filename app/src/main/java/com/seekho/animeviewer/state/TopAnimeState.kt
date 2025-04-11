package com.seekho.animeviewer.state

import com.seekho.animeviewer.model.TopAnimeResponse
import com.seekho.animeviewer.model.AnimeDetailResponse


sealed class TopAnimeState {

    object Init : TopAnimeState()

    data class TopAnimeSuccessState(val data: TopAnimeResponse) : TopAnimeState()

    data class TopAnimeDetailSuccessState(val data : AnimeDetailResponse) : TopAnimeState()

    data class ShowMessage(val msg: String) : TopAnimeState()
}