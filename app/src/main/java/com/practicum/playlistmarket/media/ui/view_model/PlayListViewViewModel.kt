package com.practicum.playlistmarket.media.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmarket.media.domain.db.PlayListInteractor
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.media.ui.states.PlayListViewState
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.launch

class PlayListViewViewModel(val interactor: PlayListInteractor)  : ViewModel() {

    private val statePlayListTracksView = MutableLiveData<PlayListViewState>()
    fun observeStateTracksView(): LiveData<PlayListViewState> = statePlayListTracksView

    private val deletePlayListState = MutableLiveData<Boolean>()
    fun observeStateDeletePlaylist(): LiveData<Boolean> = deletePlayListState

    private val deleteTrackState = MutableLiveData<Boolean>()
    fun observeStateDeleteTrack(): LiveData<Boolean> = deleteTrackState

    private val playlist = MutableLiveData<PlayList>()
    fun observePlaylist(): LiveData<PlayList> = playlist



    fun fillDataTracks(playListId : Int){
        viewModelScope.launch {
            interactor.getTracksForPlaylist(playListId).collect{tracks ->
                if(tracks.isEmpty()){
                    statePlayListTracksView.postValue(PlayListViewState.Empty)
                } else {
                    statePlayListTracksView.postValue(PlayListViewState.ShowContent(tracks))
                }
            }
        }
    }

    fun showInfoPlaylist(playListId: Int) {
        viewModelScope.launch {
            playlist.postValue(interactor.getPlayList(playListId))
        }

    }

    fun sharePlaylist(tracks : List<Track>, nameTrack : String, description: String, quantityTracks : String) {
        interactor.sharePlaylist(tracks, nameTrack, description, quantityTracks)
    }



    fun deleteTrackPlaylist(trackId : String, playlistId: Int) {
        viewModelScope.launch {
            deleteTrackState.postValue(interactor.deleteTrackPlaylist(trackId, playlistId))
        }

    }

    fun deletePlaylist(playlistId: Int) {
        viewModelScope.launch {
            deletePlayListState.postValue(interactor.deletePlaylist(playlistId))
        }
    }

}