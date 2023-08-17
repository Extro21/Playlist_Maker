package com.practicum.playlistmarket.search.domain.impl

import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.search.domain.api.SharedPreferensecHistory
import com.practicum.playlistmarket.search.domain.api.TrackHistoryInteractor

class TrackHistoryInteractorImpl(private val sharedHistory: SharedPreferensecHistory) : TrackHistoryInteractor {

    override fun addHistoryTracks(tracksHistory: ArrayList<Track>) {
        sharedHistory.addHistoryTracks(tracksHistory)
    }

    override fun editHistoryList(tracksHistory: ArrayList<Track>) {
        sharedHistory.editHistoryList(tracksHistory)
    }

    override fun clearTrack(tracksHistory: ArrayList<Track>) {
        sharedHistory.clearTrack(tracksHistory)
    }

   override fun addTrackInAdapter(track : Track){
        sharedHistory.addTrackInAdapter(track)
    }
}