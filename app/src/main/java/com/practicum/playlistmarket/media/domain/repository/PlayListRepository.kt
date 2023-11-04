package com.practicum.playlistmarket.media.domain.repository

import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlayListRepository {

    suspend fun addPlayList(name : String, description : String, uri : String, playListId : Int)

    fun getPlaylist() : Flow<List<PlayList>>


    suspend fun addTrackPlaylist(track: Track, playList: PlayList) : Boolean


    suspend fun getTracksForPlaylist(playList: PlayList) : Flow<List<Track>>

    suspend fun getTracksForPlaylistCount(playList: PlayList) : Int
}