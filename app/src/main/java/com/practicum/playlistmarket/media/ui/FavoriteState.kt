package com.practicum.playlistmarket.media.ui

import com.practicum.playlistmarket.player.domain.models.Track

sealed interface FavoriteState {

    object Loading : FavoriteState

    object Empty : FavoriteState

    data class Content(
        val track : List<Track>
    ) : FavoriteState


//    data class Empty(
//        val massage :  String
//    ) : FavoriteState


}