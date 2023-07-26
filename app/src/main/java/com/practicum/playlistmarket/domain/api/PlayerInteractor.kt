package com.practicum.playlistmarket.domain.api


interface PlayerInteractor {

    fun playbackControl()
    fun startPlayer()
    fun pausePlayer()
    fun preparePlayer(trackUrl : String)
    fun releasePlayer()

}