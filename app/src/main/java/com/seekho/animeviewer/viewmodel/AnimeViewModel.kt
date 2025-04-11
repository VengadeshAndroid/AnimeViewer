package com.seekho.animeviewer.viewmodel

import androidx.lifecycle.viewModelScope
import com.seekho.animeviewer.state.TopAnimeState
import com.seekho.animeviewer.webservice.ModelRepository
import com.seekho.animeviewer.model.State
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AnimeViewModel @Inject constructor(private val modelRepository: ModelRepository) :
    BaseViewModel<TopAnimeState>() {

    private var setTopAnimeState: TopAnimeState = TopAnimeState.Init
        set(value) {
            field = value
            setState(setTopAnimeState)
        }


    fun topAnime() {
        viewModelScope.launch {
            modelRepository.topAnime(_baseState).collect {
                when (it) {
                    is State.Success -> {
                        val topAnimeData = it.data
                        setTopAnimeState = TopAnimeState.TopAnimeSuccessState(topAnimeData)
                    }

                    is State.Error -> {
                        setTopAnimeState = TopAnimeState.ShowMessage(it.detail)
                    }

                    else -> {
                        dismissProgressBar()
                    }
                }
            }
        }
    }

    fun animeDetails(anime_id: Int?) {
        viewModelScope.launch {
            if (anime_id != null) {
                modelRepository.animeDetails(anime_id, _baseState).collect {
                    when (it) {
                        is State.Success -> {
                            setTopAnimeState =
                                TopAnimeState.TopAnimeDetailSuccessState(it.data)
                        }

                        is State.Error -> {
                            setTopAnimeState = TopAnimeState.ShowMessage(it.detail)
                        }

                        else -> {
                            dismissProgressBar()
                        }
                    }
                }
            }
        }
    }

}