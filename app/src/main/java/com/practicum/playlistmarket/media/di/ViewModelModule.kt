package com.practicum.playlistmarket.media.di

import com.practicum.playlistmarket.media.ui.view_model.PlayListViewModel
import com.practicum.playlistmarket.media.ui.view_model.FavoriteViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewMediaViewModel = module {

}

val viewFragmentFavoriteViewModel = module {

    viewModel {
        FavoriteViewModel(get())
    }

}

val viewFragmentPlayListViewModule = module {
    PlayListViewModel()
}






