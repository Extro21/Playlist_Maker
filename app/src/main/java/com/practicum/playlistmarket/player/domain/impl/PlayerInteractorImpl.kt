package com.practicum.playlistmarket.player.domain.impl

import android.util.Log
import com.practicum.playlistmarket.player.domain.api.PlayerInteractor
import com.practicum.playlistmarket.player.domain.api.PlayerListener
import com.practicum.playlistmarket.player.domain.repository.PlayerRepository



class PlayerInteractorImpl(private val repository: PlayerRepository,
                           ) : PlayerInteractor {

    override fun setListener(listener : PlayerListener){
        repository.setupListener(listener)
        Log.e("TimeLog", "time")
    }

    override fun playbackControl() {
        repository.playbackControl()
    }

    override fun startPlayer() {
        repository.startPlayer()
    }

    override fun pausePlayer() {
        repository.pausePlayer()
    }

    override fun preparePlayer(trackUrl: String) {
        repository.preparePlayer(trackUrl)
    }

    override fun releasePlayer() {
        repository.releasePlayer()
    }



}
