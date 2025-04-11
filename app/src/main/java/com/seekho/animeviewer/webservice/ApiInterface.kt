package com.seekho.animeviewer.webservice

import com.seekho.animeviewer.model.AnimeDetailResponse
import com.seekho.animeviewer.model.TopAnimeResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiInterface {

    @GET("v4/top/anime")
    suspend fun topAnime(): Response<TopAnimeResponse>

    @GET("v4/anime/{anime_id}")
    suspend fun animeDetails(@Path("anime_id") id: Int): Response<AnimeDetailResponse>

}