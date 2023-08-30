package com.practicum.playlistmarket.player.domain.api

import com.practicum.playlistmarket.player.domain.StatePlayer

interface PlayerListener {

    fun onTimeUpdate(time : String)
    fun onStateUpdate(state : StatePlayer)
}