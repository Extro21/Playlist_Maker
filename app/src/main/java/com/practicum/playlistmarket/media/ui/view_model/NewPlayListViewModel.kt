package com.practicum.playlistmarket.media.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmarket.media.domain.db.PlayListInteractor
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.media.ui.PlayListState
import kotlinx.coroutines.launch

class NewPlayListViewModel(private val interactor: PlayListInteractor) : ViewModel() {


    suspend fun addPlaylist(playList: PlayList) {
            interactor.addPlayList(playList)
    }




}