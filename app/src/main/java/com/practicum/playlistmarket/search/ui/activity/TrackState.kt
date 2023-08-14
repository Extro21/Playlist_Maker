package com.practicum.playlistmarket.search.ui.activity

import com.practicum.playlistmarket.player.domain.models.Track

sealed class TrackState {

    object Loading : TrackState()

    data class Content(
        val tracks: List<Track>
    ) : TrackState()

    data class Error(
        val errorMessage: String
    ) : TrackState()

    data class Empty(
        val message: String
    ) : TrackState()
}
