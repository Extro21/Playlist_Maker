package com.practicum.playlistmarket.player.domain.impl

import com.practicum.playlistmarket.player.domain.StatePlayer
import com.practicum.playlistmarket.player.domain.api.TrackStateListener

class TrackStateListenerImpl : TrackStateListener  {

    override fun getState(state: StatePlayer) : StatePlayer {
        return state
    }
}