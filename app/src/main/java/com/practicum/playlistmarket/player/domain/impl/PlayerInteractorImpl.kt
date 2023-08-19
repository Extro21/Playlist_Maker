package com.practicum.playlistmarket.player.domain.impl

import com.practicum.playlistmarket.player.domain.StatePlayer
import com.practicum.playlistmarket.player.domain.api.PlayerInteractor
import com.practicum.playlistmarket.player.domain.api.PlayerListener
import com.practicum.playlistmarket.player.domain.api.TrackTimeListener
import com.practicum.playlistmarket.player.domain.repository.PlayerRepository
import com.practicum.playlistmarket.player.ui.view_model.MediaPlayerViewModel


class PlayerInteractorImpl(private val repository: PlayerRepository,
                           ) : PlayerInteractor {

    override fun setListener(listener : PlayerListener){
        repository.setupListener(listener)
    }

//    override fun setListenerToPlayer(listener:PlayerListener){
//        repository.setupListener(listener)
//    }

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

    override fun getTime(): String {
        return repository.getTime()
    }

    override fun getState(): StatePlayer {
      return  repository.getState()
    }





}
