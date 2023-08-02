package com.practicum.playlistmarket.Creator

import com.practicum.playlistmarket.data.repository.PlayerRepositoryImpl
import com.practicum.playlistmarket.domain.api.TrackStateListener
import com.practicum.playlistmarket.domain.api.TrackTimeListener
import com.practicum.playlistmarket.domain.api.PlayerInteractor
import com.practicum.playlistmarket.domain.impl.PlayerInteractorImpl
import com.practicum.playlistmarket.domain.repository.PlayerRepository


object Creator {


    private fun getPlayerRepository(
        trackTimeListener: TrackTimeListener,
        stateListener: TrackStateListener
    ): PlayerRepository {
        return PlayerRepositoryImpl(trackTimeListener, stateListener)
    }


    fun providePlayerInteractor(
        trackTimeListener: TrackTimeListener,
        stateListener: TrackStateListener
    ): PlayerInteractor {
        return PlayerInteractorImpl(getPlayerRepository(trackTimeListener, stateListener))
    }


}