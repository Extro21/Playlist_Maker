package com.practicum.playlistmarket.media.ui.states

import com.practicum.playlistmarket.media.domain.module.PlayList

sealed interface PlayListCreateAndNewState {

    class CreatePlaylist(
        val playList: PlayList
    ) : PlayListCreateAndNewState

    object NewPlaylist : PlayListCreateAndNewState

}