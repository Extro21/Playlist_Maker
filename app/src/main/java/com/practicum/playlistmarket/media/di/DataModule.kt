package com.practicum.playlistmarket.media.di

import androidx.room.Room
import com.practicum.playlistmarket.media.data.db.AppDataBase
import com.practicum.playlistmarket.media.data.db.PlayListConvector
import com.practicum.playlistmarket.media.data.db.TrackDbConvertor
import com.practicum.playlistmarket.media.data.repository.FavoriteRepositoryImpl
import com.practicum.playlistmarket.media.data.repository.PlayListRepositoryImpl
import com.practicum.playlistmarket.media.domain.repository.FavoriteRepository
import com.practicum.playlistmarket.media.domain.repository.PlayListRepository
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    factory {
        TrackDbConvertor()
    }

    factory {
        PlayListConvector()
    }

    single<FavoriteRepository> {
        FavoriteRepositoryImpl(get(), get())
    }

    single<PlayListRepository> {
        PlayListRepositoryImpl(get(), get())
    }
}

val dataModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDataBase::class.java, "databaseAp9.db").build()
    }
}