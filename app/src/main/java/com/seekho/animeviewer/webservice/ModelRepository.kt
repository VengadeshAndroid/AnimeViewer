package com.seekho.animeviewer.webservice

import android.content.Context
import com.seekho.animeviewer.model.AnimeDetailResponse
import com.seekho.animeviewer.model.State
import com.seekho.animeviewer.model.TopAnimeResponse
import com.seekho.animeviewer.state.BaseState
import com.seekho.animeviewer.util.sharedpreference.SharedPrefManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject

class ModelRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val sharedPrefManager: SharedPrefManager,
    private val apiClient: ApiClient
) {

    fun topAnime(baseFlow: MutableSharedFlow<BaseState>?): Flow<State<TopAnimeResponse>> {
        return object : NetworkBoundRepository<TopAnimeResponse>(
            baseFlow = baseFlow, sharedPrefManager = sharedPrefManager, context = context
        ) {
            override suspend fun fetchData() = apiClient.getApiClient().topAnime()
        }.asFlow()
    }

    fun animeDetails(anime_id:Int,
                       baseFlow: MutableSharedFlow<BaseState>?): Flow<State<AnimeDetailResponse>> {
        return object : NetworkBoundRepository<AnimeDetailResponse>(
            baseFlow = baseFlow, sharedPrefManager = sharedPrefManager, context = context
        ) {
            override suspend fun fetchData() = apiClient.getApiClient().animeDetails(anime_id)
        }.asFlow()
    }

}