package com.practicum.playlistmarket.search.ui.di

import com.practicum.playlistmarket.search.data.NetworkClient
import com.practicum.playlistmarket.search.data.SharedPreferencesHistoryImpl
import com.practicum.playlistmarket.search.data.dto.TrackRepositoryImpl
import com.practicum.playlistmarket.search.data.network.RetrofitNetworkClient
import com.practicum.playlistmarket.search.domain.api.SharedPreferensecHistory
import com.practicum.playlistmarket.search.domain.api.TrackRepository
import org.koin.dsl.module

val dataSearchModule = module {
    single<TrackRepository> {
        TrackRepositoryImpl(networkClient = get())
    }

    single<NetworkClient> {
        RetrofitNetworkClient()
    }

    single<SharedPreferensecHistory> {
        SharedPreferencesHistoryImpl(context = get())
    }
}