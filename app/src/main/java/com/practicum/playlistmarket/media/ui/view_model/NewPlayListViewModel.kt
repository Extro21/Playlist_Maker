package com.practicum.playlistmarket.media.ui.view_model

import android.net.Uri
import androidx.lifecycle.ViewModel
import com.practicum.playlistmarket.media.domain.db.PlayListInteractor
import com.practicum.playlistmarket.media.domain.module.PlayList

class NewPlayListViewModel(private val interactor: PlayListInteractor) : ViewModel() {


    suspend fun addPlaylist(name : String, description : String, uri : String, playListId : Int) {
            interactor.addPlayList(name, description, uri, playListId)
    }

    fun addImageStorage(uri: Uri){
        interactor.saveImageToPrivateStorage(uri)
    }


    fun getUri(uriPlaylist: String, path: String): String{
        return interactor.getUri(uriPlaylist, path)
    }

}