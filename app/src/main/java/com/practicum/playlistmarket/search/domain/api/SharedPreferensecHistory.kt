package com.practicum.playlistmarket.search.domain.api

import com.practicum.playlistmarket.player.domain.models.Track

interface SharedPreferensecHistory {

    fun addHistoryTracks(tracksHistory : ArrayList<Track>)
    fun editHistoryList(tracksHistory : ArrayList<Track>)
    fun clearTrack(tracksHistory : ArrayList<Track>)

    fun addTrackInAdapter(track : Track)
}