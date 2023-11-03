package com.practicum.playlistmarket.media.ui.states

import com.practicum.playlistmarket.player.domain.models.Track

sealed interface PlayListStateTracks {
    object Empty : PlayListStateTracks

    data class Content(
        val tracks: List<Track>
    ) : PlayListStateTracks


}