package com.practicum.playlistmarket.media.di

import com.practicum.playlistmarket.media.domain.db.FavoriteInteractor
import com.practicum.playlistmarket.media.domain.impl.FavoriteInteractorImpl
import org.koin.dsl.module

val interactorFavoriteModule = module {

    single<FavoriteInteractor> {
        FavoriteInteractorImpl(get())
    }

}