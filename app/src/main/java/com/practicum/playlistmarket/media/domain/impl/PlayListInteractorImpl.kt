package com.practicum.playlistmarket.media.domain.impl

import android.net.Uri
import android.util.Log
import com.practicum.playlistmarket.media.domain.db.PlayListInteractor
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.media.domain.repository.ImageRepository
import com.practicum.playlistmarket.media.domain.repository.PlayListRepository
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlayListInteractorImpl(val playListRepository: PlayListRepository, val imageRepository: ImageRepository) : PlayListInteractor {
    override suspend fun getTracksForPlaylist(playList: PlayList): Flow<List<Track>> {
        return playListRepository.getTracksForPlaylist(playList)
    }

    override suspend fun addPlayList(name : String, description : String, uri : String) {
        playListRepository.addPlayList(name, description, uri)
    }

    override fun getPlayList(): Flow<List<PlayList>> {
        return playListRepository.getPlaylist()
    }


    override suspend fun addTrackPlaylist(track: Track, playList: PlayList) : Boolean {
        Log.e("addTrackPlaylist", "Interactor")
      return  playListRepository.addTrackPlaylist(track, playList)
    }

    override suspend fun getTracksForPlaylistCount(playList: PlayList): Int {
        return playListRepository.getTracksForPlaylistCount(playList)
    }

     override fun saveImageToPrivateStorage(uri: Uri) {
         imageRepository.saveImageToPrivateStorage(uri)
     }

    override fun getUri(uriPlaylist: String, path: String): String {
       return imageRepository.getUri(uriPlaylist, path)
    }
}