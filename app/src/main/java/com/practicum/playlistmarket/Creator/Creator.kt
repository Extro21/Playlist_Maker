package com.practicum.playlistmarket.Creator

import android.content.Context
import com.practicum.playlistmarket.player.data.repository.PlayerRepositoryImpl
import com.practicum.playlistmarket.player.domain.api.TrackStateListener
import com.practicum.playlistmarket.player.domain.api.TrackTimeListener
import com.practicum.playlistmarket.player.domain.api.PlayerInteractor
import com.practicum.playlistmarket.player.domain.impl.PlayerInteractorImpl
import com.practicum.playlistmarket.player.domain.repository.PlayerRepository
import com.practicum.playlistmarket.search.data.SharedPreferencesHistoryImpl
import com.practicum.playlistmarket.search.data.dto.TrackRepositoryImpl
import com.practicum.playlistmarket.search.data.network.RetrofitNetworkClient
import com.practicum.playlistmarket.search.domain.api.SharedPreferensecHistory
import com.practicum.playlistmarket.search.domain.api.TrackHistoryInteractor
import com.practicum.playlistmarket.search.domain.api.TrackInteractor
import com.practicum.playlistmarket.search.domain.api.TrackRepository
import com.practicum.playlistmarket.search.domain.impl.TrackHistoryInteractorImpl
import com.practicum.playlistmarket.search.domain.impl.TrackInteraktorImpl
import com.practicum.playlistmarket.settings.data.repository.SettingRepositoryImpl
import com.practicum.playlistmarket.settings.data.SharedPreferencesThemeSettings
import com.practicum.playlistmarket.settings.domain.api.SettingInteractor
import com.practicum.playlistmarket.settings.domain.impl.SettingInteractorImpl
import com.practicum.playlistmarket.settings.domain.api.SettingRepository


object Creator {


    private fun getPlayerRepository(stateListener: TrackStateListener): PlayerRepository {
        return PlayerRepositoryImpl(stateListener)
    }


    fun providePlayerInteractor(stateListener: TrackStateListener): PlayerInteractor {
        return PlayerInteractorImpl(getPlayerRepository(stateListener))
    }


//    private fun getTrackRepository(): TrackRepository {
//        return TrackRepositoryImpl(RetrofitNetworkClient())
//    }

//    fun provideTrackInteractor(): TrackInteractor {
//        return TrackInteraktorImpl(getTrackRepository())
//    }

//    private fun getSettingRepository(context: Context): SettingRepository {
//        return SettingRepositoryImpl(context, SharedPreferencesThemeSettings(context))
//    }

//    fun provideSettingInteractor(context: Context): SettingInteractor {
//        return SettingInteractorImpl(getSettingRepository(context))
//    }
//
//   private fun getHistoryRepository(context: Context) : SharedPreferensecHistory {
//            return SharedPreferencesHistoryImpl(context)
//    }

//    fun provideHistoryInteractor(context: Context) : TrackHistoryInteractor {
//        return TrackHistoryInteractorImpl(getHistoryRepository(context))
//    }


}