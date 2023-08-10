package com.practicum.playlistmarket.player.domain.api

import com.practicum.playlistmarket.util.StatePlayer

interface TrackStateListener {

    fun getState(state : StatePlayer)

}