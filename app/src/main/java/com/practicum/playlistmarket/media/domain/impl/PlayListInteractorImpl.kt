package com.practicum.playlistmarket.media.domain.impl

import android.net.Uri
import android.util.Log
import com.practicum.playlistmarket.media.domain.db.PlayListInteractor
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.media.domain.repository.ImageRepository
import com.practicum.playlistmarket.media.domain.repository.PlayListRepository
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlayListInteractorImpl(
    val playListRepository: PlayListRepository,
    val imageRepository: ImageRepository
) : PlayListInteractor {


    override suspend fun deletePlaylist(playlistId: Int): Boolean {
        return playListRepository.deletePlaylist(playlistId)
    }

    override suspend fun deleteTrackPlaylist(trackId: String, playlistId: Int): Boolean {
        return playListRepository.deleteTrackPlaylist(trackId, playlistId)
    }


    override suspend fun getTracksForPlaylist(playListId: Int): Flow<List<Track>> {
        return playListRepository.getTracksForPlaylist(playListId)
    }

    override suspend fun addPlayList(name: String, description: String, uri: String) {
        playListRepository.addPlayList(name, description, uri)
    }

    override fun getPlayList(): Flow<List<PlayList>> {
        return playListRepository.getPlaylist()
    }

    override suspend fun getPlayList(id: Int): PlayList {
        return playListRepository.getPlayList(id)
    }

    override suspend fun updatePlaylist(name: String, description: String, uri: String, id: Int) {
        playListRepository.updatePlaylist(name, description, uri, id)
    }

    override fun sharePlaylist(
        tracks: List<Track>,
        nameTrack: String,
        description: String,
        quantityTracks: String
    ) {
        playListRepository.sharePlaylist(tracks, nameTrack, description, quantityTracks)
    }

    override suspend fun addTrackPlaylist(track: Track, playList: PlayList): Boolean {
        Log.e("addTrackPlaylist", "Interactor")
        return playListRepository.addTrackPlaylist(track, playList)
    }

    override suspend fun getTracksForPlaylistCount(playList: PlayList): Int {
        return playListRepository.getTracksForPlaylistCount(playList)
    }

    override fun saveImageToPrivateStorage(uri: Uri) {
        imageRepository.saveImageToPrivateStorage(uri)
    }

    override fun getUri(uriPlaylist: String): String {
        return imageRepository.getUri(uriPlaylist)
    }
}