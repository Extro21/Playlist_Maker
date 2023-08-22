package com.practicum.playlistmarket.player.di

import com.practicum.playlistmarket.player.domain.api.PlayerInteractor
import com.practicum.playlistmarket.player.domain.impl.PlayerInteractorImpl
import org.koin.dsl.module

val domainPlayerModule = module {
    factory<PlayerInteractor> {
        PlayerInteractorImpl(repository = get())
    }
}