package com.practicum.playlistmarket.player.domain.api


import com.practicum.playlistmarket.player.domain.StatePlayer

interface PlayerListener {


     fun onStateUpdate(state : StatePlayer)

}