package com.practicum.playlistmarket.media.ui.states

import com.practicum.playlistmarket.player.domain.models.Track

sealed interface PlayListViewState{
    object Empty : PlayListViewState

    class ShowContent(
        val tracks : List<Track>
    ) : PlayListViewState
}


