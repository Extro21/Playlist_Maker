package com.practicum.playlistmarket.media.ui.view_model

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.practicum.playlistmarket.media.domain.db.PlayListInteractor
import com.practicum.playlistmarket.media.domain.module.PlayList

class NewPlayListViewModel(private val interactor: PlayListInteractor) : ViewModel() {


    suspend fun addPlaylist(playList: PlayList) {
            interactor.addPlayList(playList)
    }

    fun addImageStorage(uri: Uri){
        interactor.saveImageToPrivateStorage(uri)
    }




}