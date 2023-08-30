package com.practicum.playlistmarket.player.domain.repository

import com.practicum.playlistmarket.player.domain.api.PlayerListener

interface PlayerRepository {

    fun playbackControl()
    fun startPlayer()
    fun pausePlayer()
    fun preparePlayer(trackUrl: String)
    fun releasePlayer()
    fun updateTime(time : String)

    fun setupListener(listener: PlayerListener)

}