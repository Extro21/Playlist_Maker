package com.practicum.playlistmarket.media.domain.db

import android.net.Uri
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlayListInteractor {

    suspend fun deletePlaylist(playlistId: Int) : Boolean


    suspend fun deleteTrackPlaylist(trackId : String, playlistId: Int): Boolean

    suspend fun addPlayList(name : String, description : String, uri : String)

    fun getPlayList(): Flow<List<PlayList>>


    suspend fun addTrackPlaylist(track: Track, playList: PlayList) : Boolean

    suspend fun getTracksForPlaylist(playListId: Int): Flow<List<Track>>

    suspend fun getTracksForPlaylistCount(playList: PlayList) : Int

    fun saveImageToPrivateStorage(uri: Uri)

    fun getUri(uriPlaylist: String): String


    suspend fun getPlayList(id: Int): PlayList

    suspend fun updatePlaylist(name: String, description: String, uri: String, id: Int)

    fun sharePlaylist(tracks : List<Track>, nameTrack : String, description: String, quantityTracks : String)

}