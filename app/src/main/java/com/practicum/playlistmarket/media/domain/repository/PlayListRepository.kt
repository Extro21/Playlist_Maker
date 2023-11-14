package com.practicum.playlistmarket.media.domain.repository

import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlayListRepository {

    suspend fun deletePlaylist(playlistId: Int) : Boolean

    suspend fun deleteTrackPlaylist(trackId : String, playlistId: Int): Boolean

    suspend fun addPlayList(name : String, description : String, uri : String)

    fun getPlaylist() : Flow<List<PlayList>>


    suspend fun addTrackPlaylist(track: Track, playList: PlayList) : Boolean


    suspend fun getTracksForPlaylist(playListId: Int) : Flow<List<Track>>

    suspend fun getTracksForPlaylistCount(playList: PlayList) : Int



    suspend fun getPlayList(id: Int): PlayList

    suspend fun updatePlaylist(name: String, description: String, uri: String, id: Int)

    fun sharePlaylist(tracks : List<Track>, nameTrack : String, description: String, quantityTracks : String)
}