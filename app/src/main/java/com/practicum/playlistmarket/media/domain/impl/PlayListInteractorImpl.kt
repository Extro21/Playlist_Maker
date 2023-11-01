package com.practicum.playlistmarket.media.domain.impl

import android.util.Log
import com.practicum.playlistmarket.media.domain.db.PlayListInteractor
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.media.domain.repository.PlayListRepository
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

class PlayListInteractorImpl(val playListRepository: PlayListRepository) : PlayListInteractor {
    override suspend fun getTracksForPlaylist(playList: PlayList): Flow<List<Track>> {
        return playListRepository.getTracksForPlaylist(playList)
    }

    override suspend fun addPlayList(playList: PlayList) {
        playListRepository.addPlayList(playList)
    }

    override fun getPlayList(): Flow<List<PlayList>> {
        return playListRepository.getPlaylist()
    }


    override suspend fun addTrackPlaylist(track: Track, playList: PlayList) : Boolean {
      return  playListRepository.addTrackPlaylist(track, playList)
    }

    override suspend fun getTracksForPlaylistCount(playList: PlayList): Int {
        return playListRepository.getTracksForPlaylistCount(playList)
    }
}