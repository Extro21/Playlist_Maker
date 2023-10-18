package com.practicum.playlistmarket

import android.app.Application
import com.practicum.playlistmarket.media.di.dataModule
import com.practicum.playlistmarket.media.di.interactorFavoriteModule
import com.practicum.playlistmarket.media.di.repositoryModule
import com.practicum.playlistmarket.media.di.viewFragmentPlayListViewModule
import com.practicum.playlistmarket.media.di.viewFragmentFavoriteViewModel
import com.practicum.playlistmarket.media.di.viewMediaViewModel
import com.practicum.playlistmarket.player.di.dataPlayerModule
import com.practicum.playlistmarket.player.di.domainPlayerModule
import com.practicum.playlistmarket.player.di.viewPlayerModelModule
import com.practicum.playlistmarket.search.di.dataSearchModule
import com.practicum.playlistmarket.search.di.domainSearchModule
import com.practicum.playlistmarket.search.di.viewSearchViewModel
import com.practicum.playlistmarket.settings.domain.api.SettingInteractor
import com.practicum.playlistmarket.settings.di.dataSettingModule
import com.practicum.playlistmarket.settings.di.domainSettingModule
import com.practicum.playlistmarket.settings.di.viewSettingModelModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)

            modules(
                listOf(
                    viewSettingModelModule,
                    viewSearchViewModel,
                    viewPlayerModelModule, dataSettingModule, domainSettingModule,
                    dataPlayerModule, domainPlayerModule,
                    domainSearchModule, dataSearchModule,
                    viewMediaViewModel, viewFragmentPlayListViewModule, viewFragmentFavoriteViewModel,
                    dataModule,repositoryModule,interactorFavoriteModule
                )
            )
        }

        val settingInteractor : SettingInteractor by inject()
        val darkTheme = settingInteractor.defaultChange()
        settingInteractor.changeTheme(darkTheme)
    }

}