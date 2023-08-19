package com.practicum.playlistmarket.settings.ui.di

import com.practicum.playlistmarket.settings.ui.view_model.SettingViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewSettingModelModule = module {
    viewModel {
        SettingViewModel(application = get(), settingInteractor =  get())
    }

}