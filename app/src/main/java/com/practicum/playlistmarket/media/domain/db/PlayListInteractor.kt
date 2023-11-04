package com.practicum.playlistmarket.media.domain.db

import android.net.Uri
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlayListInteractor {


    suspend fun addPlayList(name : String, description : String, uri : String)

    fun getPlayList(): Flow<List<PlayList>>


    suspend fun addTrackPlaylist(track: Track, playList: PlayList) : Boolean

    suspend fun getTracksForPlaylist(playList: PlayList): Flow<List<Track>>

    suspend fun getTracksForPlaylistCount(playList: PlayList) : Int

    fun saveImageToPrivateStorage(uri: Uri)

    fun getUri(uriPlaylist: String, path: String): String

}