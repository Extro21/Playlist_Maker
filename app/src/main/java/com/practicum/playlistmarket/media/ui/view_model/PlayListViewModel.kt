package com.practicum.playlistmarket.media.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmarket.media.domain.db.PlayListInteractor
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.media.ui.states.PlayListState
import com.practicum.playlistmarket.media.ui.states.PlayListStateTracks
import kotlinx.coroutines.launch

class PlayListViewModel(val interactor: PlayListInteractor) : ViewModel() {


    private val statePlayList = MutableLiveData<PlayListState>()
    fun observerState(): LiveData<PlayListState> = statePlayList

    private val statePlayListTracks = MutableLiveData<PlayListStateTracks>()
    fun observerStateTracks(): LiveData<PlayListStateTracks> = statePlayListTracks

    fun fillData() {
        statePlayList.postValue(PlayListState.Loading)

        viewModelScope.launch {
            interactor.getPlayList().collect() {
                processResult(it)
            }
        }
    }

    private fun processResult(playList: List<PlayList>) {
        if (playList.isEmpty()) {
            statePlayList.postValue(PlayListState.Empty)
        } else {
            statePlayList.postValue(PlayListState.Content(playList))
        }
    }



    suspend fun getTrackCount(playList: PlayList): Int {
        return interactor.getTracksForPlaylistCount(playList)
    }



}