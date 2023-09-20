package com.practicum.playlistmarket.search.domain.api

import com.practicum.playlistmarket.player.domain.models.Track

interface SharedPreferensecHistory {

    fun editHistoryList()

    fun clearTrack()

    fun getAllTracks(): List<Track>

    fun saveTrack(track: Track)


}