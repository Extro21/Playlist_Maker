package com.practicum.playlistmarket.media.ui.view_model

import android.net.Uri
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmarket.media.domain.db.PlayListInteractor
import com.practicum.playlistmarket.media.ui.states.PlayListCreateAndNewState
import kotlinx.coroutines.launch

class NewPlayListViewModel(private val interactor: PlayListInteractor) : ViewModel() {

    private val stateNewAndCreatePlaylist = MutableLiveData<PlayListCreateAndNewState>()
    fun observeStateNewAndCreatePlaylist(): LiveData<PlayListCreateAndNewState> =
        stateNewAndCreatePlaylist


    fun checkStateCreateAndNew(id: Int) {
        Log.d("checkId", id.toString())
        if (id == 0) {
            stateNewAndCreatePlaylist.postValue(PlayListCreateAndNewState.NewPlaylist)
        } else {
            viewModelScope.launch {
                val playlist = interactor.getPlayList(id)
                stateNewAndCreatePlaylist.postValue(
                    PlayListCreateAndNewState.CreatePlaylist(
                        playlist
                    )
                )
            }
        }
    }

    fun updatePlaylist(name: String, description: String, uri: String, id: Int) {
        viewModelScope.launch {
            interactor.updatePlaylist(name, description, uri, id)
        }
    }


    suspend fun addPlaylist(name: String, description: String, uri: String) {
        interactor.addPlayList(name, description, uri)
    }

    fun addImageStorage(uri: Uri) {
        interactor.saveImageToPrivateStorage(uri)
    }


    fun getUri(uriPlaylist: String): String {
        return interactor.getUri(uriPlaylist)
    }

}