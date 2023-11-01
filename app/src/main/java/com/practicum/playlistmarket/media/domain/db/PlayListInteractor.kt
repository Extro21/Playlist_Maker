package com.practicum.playlistmarket.media.domain.db

import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface PlayListInteractor {


    suspend fun addPlayList(playList: PlayList)

    fun getPlayList(): Flow<List<PlayList>>


    suspend fun addTrackPlaylist(track: Track, playList: PlayList) : Boolean

    suspend fun getTracksForPlaylist(playList: PlayList): Flow<List<Track>>

    suspend fun getTracksForPlaylistCount(playList: PlayList) : Int

}