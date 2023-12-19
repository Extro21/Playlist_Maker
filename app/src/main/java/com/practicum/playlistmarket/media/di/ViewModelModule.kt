package com.practicum.playlistmarket.media.di

import com.practicum.playlistmarket.media.ui.view_model.PlayListViewModel
import com.practicum.playlistmarket.media.ui.view_model.FavoriteViewModel
import com.practicum.playlistmarket.media.ui.view_model.NewPlayListViewModel
import com.practicum.playlistmarket.media.ui.view_model.PlayListViewViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewMediaViewModel = module {

}

val viewFragmentFavoriteViewModel = module {

    viewModel {
        FavoriteViewModel(get())
    }

    viewModel {
        NewPlayListViewModel(get())
    }

    viewModel {
        PlayListViewViewModel(get())
    }

}

val viewFragmentPlayListViewModule = module {

    viewModel {
        PlayListViewModel(get())
    }


}






