package com.practicum.playlistmarket.player.domain.repository

import com.practicum.playlistmarket.player.domain.StatePlayer
import com.practicum.playlistmarket.player.domain.api.PlayerListener

interface PlayerRepository {



    fun playbackControl()
    fun startPlayer()
    fun pausePlayer()
    fun preparePlayer(trackUrl: String)
    fun releasePlayer()
    fun updateTime(time : String)
    fun getTime() : String

    fun getState() : StatePlayer



    fun setupListener(listener: PlayerListener)

}