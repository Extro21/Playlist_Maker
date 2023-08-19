package com.practicum.playlistmarket.search.ui.di

import com.practicum.playlistmarket.search.domain.api.TrackHistoryInteractor
import com.practicum.playlistmarket.search.domain.api.TrackInteractor
import com.practicum.playlistmarket.search.domain.impl.TrackHistoryInteractorImpl
import com.practicum.playlistmarket.search.domain.impl.TrackInteraktorImpl
import org.koin.dsl.module

val domainSearchModule = module {

    factory<TrackInteractor> {
        TrackInteraktorImpl(repository = get())
    }

    factory<TrackHistoryInteractor> {
        TrackHistoryInteractorImpl(sharedHistory = get())
    }

}