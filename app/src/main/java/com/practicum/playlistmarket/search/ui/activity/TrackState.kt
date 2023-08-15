package com.practicum.playlistmarket.search.ui.activity

import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.search.domain.SearchStatus

sealed class TrackState {

    object Loading : TrackState()

    data class Content(
        val tracks: List<Track>
    ) : TrackState()
//
//    data class Error(
//        val errorMessage: String
//    ) : TrackState()
//
//    data class Empty(
//        val message: String
//    ) : TrackState()

    data class Error(
        val errorMessage: SearchStatus
    ) : TrackState()

    data class Empty(
        val message: SearchStatus
    ) : TrackState()
}
