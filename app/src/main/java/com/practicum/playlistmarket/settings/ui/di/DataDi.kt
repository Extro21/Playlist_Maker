package com.practicum.playlistmarket.settings.ui.di

import com.practicum.playlistmarket.settings.data.SharedPreferencesThemeSettings
import com.practicum.playlistmarket.settings.data.repository.SettingRepositoryImpl
import com.practicum.playlistmarket.settings.domain.api.SettingRepository
import org.koin.dsl.module

val dataSettingModule = module {

    single<SharedPreferencesThemeSettings> {
        SharedPreferencesThemeSettings(context = get())
    }

    single<SettingRepository> {
        SettingRepositoryImpl(context = get(), sharedPreferencesThemeSettings = get())
    }

}