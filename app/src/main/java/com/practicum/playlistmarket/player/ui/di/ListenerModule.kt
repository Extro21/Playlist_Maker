package com.practicum.playlistmarket.player.ui.di

import com.practicum.playlistmarket.player.domain.api.TrackStateListener
import com.practicum.playlistmarket.player.domain.api.TrackTimeListener
import com.practicum.playlistmarket.player.domain.impl.TrackStateListenerImpl
import com.practicum.playlistmarket.player.domain.impl.TrackTimeListenerImpl
import com.practicum.playlistmarket.player.ui.view_model.MediaPlayerViewModel
import org.koin.dsl.module

val timeListenerModule = module {
/*    single<TrackTimeListener> {
       TrackTimeListenerImpl(get())

    }
}

val stateListenerModule = module {
    single<TrackStateListener> {
        TrackStateListenerImpl()
    }*/
}