package com.practicum.playlistmarket.search.domain.api

import com.practicum.playlistmarket.player.domain.models.Track

interface TrackHistoryInteractor {

    fun editHistoryList(tracksHistory : ArrayList<Track>)
    fun clearTrack()

    fun getAllTracks() : List<Track>

    fun saveTrack(track: Track)
}