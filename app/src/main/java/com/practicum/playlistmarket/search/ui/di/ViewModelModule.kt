package com.practicum.playlistmarket.search.ui.di

import com.practicum.playlistmarket.search.ui.view_model.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewSearchViewModel = module {
    viewModel{
        SearchViewModel(application = get(),
            interactorHistory = get(), interactorSearch = get())
    }
}