package com.practicum.playlistmarket.player.domain.api

interface PlayerListener {

    fun onTimeUpdate(time : String)
}