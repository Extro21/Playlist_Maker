package com.practicum.playlistmarket.media.di

import com.practicum.playlistmarket.media.domain.db.FavoriteInteractor
import com.practicum.playlistmarket.media.domain.db.PlayListInteractor
import com.practicum.playlistmarket.media.domain.impl.FavoriteInteractorImpl
import com.practicum.playlistmarket.media.domain.impl.PlayListInteractorImpl
import org.koin.dsl.module

val interactorFavoriteModule = module {

    single<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }

    single<PlayListInteractor> {
        PlayListInteractorImpl(get())
    }

}