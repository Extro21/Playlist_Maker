package com.practicum.playlistmarket.player.di

import com.practicum.playlistmarket.player.ui.view_model.MediaPlayerViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewPlayerModelModule = module {
//    viewModel{
//        MediaPlayerViewModel(playerInteractor = get())
//    }


    viewModel{
        MediaPlayerViewModel(get())
    }

}