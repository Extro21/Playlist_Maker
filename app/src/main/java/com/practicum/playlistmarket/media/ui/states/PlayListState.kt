package com.practicum.playlistmarket.media.ui.states

import com.practicum.playlistmarket.media.domain.module.PlayList

sealed interface PlayListState {

    object Loading : PlayListState

    object Empty : PlayListState

    data class Content(
        val playList: List<PlayList>
    ) : PlayListState


}