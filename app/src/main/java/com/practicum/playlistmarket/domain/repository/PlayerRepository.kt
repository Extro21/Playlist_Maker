package com.practicum.playlistmarket.domain.repository

interface PlayerRepository {



    fun playbackControl()
    fun startPlayer()
    fun pausePlayer()
    fun preparePlayer(trackUrl: String)
    fun releasePlayer()
    fun updateTime(time : String)
    fun getTime() : String

}