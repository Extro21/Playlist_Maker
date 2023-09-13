package com.practicum.playlistmarket.media.di

import com.practicum.playlistmarket.media.ui.view_model.PlayListViewModel
import com.practicum.playlistmarket.media.ui.view_model.SelectedViewModel
import org.koin.dsl.module

val viewMediaViewModel = module {

}

val viewFragmentSelectedViewModel = module {
    SelectedViewModel()
}

val viewFragmentPlayListViewModule = module {
    PlayListViewModel()
}