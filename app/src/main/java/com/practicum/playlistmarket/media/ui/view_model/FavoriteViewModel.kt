package com.practicum.playlistmarket.media.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmarket.media.domain.db.FavoriteInteractor
import com.practicum.playlistmarket.media.ui.states.FavoriteState
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.launch

class FavoriteViewModel(
   private val favoriteInteractor: FavoriteInteractor
) : ViewModel() {

    private val stateLiveData = MutableLiveData<FavoriteState>()
    fun observeState(): LiveData<FavoriteState> = stateLiveData

    fun fillData() {
        viewModelScope.launch {
            favoriteInteractor.favoriteTracks().collect { tracks ->
                processResult(tracks)
            }
        }
    }

    private fun processResult(track: List<Track>) {
        if (track.isEmpty()) {
            renderState(FavoriteState.Empty)
        } else {
            renderState(FavoriteState.Content(track))
        }
    }

    private fun renderState(state: FavoriteState) {
        stateLiveData.postValue(state)
    }


}