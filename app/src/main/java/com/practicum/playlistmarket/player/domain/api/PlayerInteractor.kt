package com.practicum.playlistmarket.player.domain.api


interface PlayerInteractor {

    fun playbackControl()
    fun startPlayer()
    fun pausePlayer()
    fun preparePlayer(trackUrl : String)
    fun releasePlayer()


    fun setListener(listener : PlayerListener)

}