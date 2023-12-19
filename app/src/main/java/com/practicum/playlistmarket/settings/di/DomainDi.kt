package com.practicum.playlistmarket.settings.di

import com.practicum.playlistmarket.settings.domain.api.SettingInteractor
import com.practicum.playlistmarket.settings.domain.impl.SettingInteractorImpl
import org.koin.dsl.module

val domainSettingModule = module {
    factory<SettingInteractor> {
        SettingInteractorImpl(repository = get())
    }
}