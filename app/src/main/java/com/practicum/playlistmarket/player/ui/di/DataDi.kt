package com.practicum.playlistmarket.player.ui.di

import com.practicum.playlistmarket.player.data.repository.PlayerRepositoryImpl
import com.practicum.playlistmarket.player.domain.repository.PlayerRepository
import org.koin.dsl.module

val dataPlayerModule = module {
    single<PlayerRepository> {
        PlayerRepositoryImpl(get())
    }


}