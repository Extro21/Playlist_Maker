package com.practicum.playlistmarket.player.domain.api

import com.practicum.playlistmarket.player.domain.StatePlayer


interface PlayerInteractor {

    fun playbackControl()
    fun startPlayer()
    fun pausePlayer()
    fun preparePlayer(trackUrl : String)
    fun releasePlayer()

    fun getState() : StatePlayer

}