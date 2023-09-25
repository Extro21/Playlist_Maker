package com.practicum.playlistmarket.search.domain.impl

import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.search.domain.api.SharedPreferensecHistory
import com.practicum.playlistmarket.search.domain.api.TrackHistoryInteractor

class TrackHistoryInteractorImpl(private val sharedHistory: SharedPreferensecHistory) : TrackHistoryInteractor {

    override fun editHistoryList(tracksHistory: ArrayList<Track>) {
        sharedHistory.editHistoryList()
    }

    override fun saveTrack(track: Track) {
        sharedHistory.saveTrack(track)
    }


    override fun clearTrack() {
        sharedHistory.clearTrack()
    }

     override fun getAllTracks() : List<Track> {
        return sharedHistory.getAllTracks()
     }
}