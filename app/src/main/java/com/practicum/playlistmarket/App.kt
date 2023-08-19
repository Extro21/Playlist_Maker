package com.practicum.playlistmarket

import android.app.Application
import com.practicum.playlistmarket.Creator.Creator
import com.practicum.playlistmarket.player.ui.di.*
import com.practicum.playlistmarket.search.ui.di.dataSearchModule
import com.practicum.playlistmarket.search.ui.di.domainSearchModule
import com.practicum.playlistmarket.search.ui.di.viewSearchViewModel
import com.practicum.playlistmarket.settings.domain.api.SettingInteractor
import com.practicum.playlistmarket.settings.ui.di.dataSettingModule
import com.practicum.playlistmarket.settings.ui.di.domainSettingModule
import com.practicum.playlistmarket.settings.ui.di.viewSettingModelModule
import org.koin.android.ext.android.inject
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App() : Application() {

    //private lateinit var interactor: SettingInteractor

   //  private val interactor : SettingInteractor by inject()

    override fun onCreate() {
        super.onCreate()

      //  interactor = Creator.provideSettingInteractor(applicationContext)
//        val darkTheme = interactor.defaultChange()
//        switchTheme(darkTheme)


        startKoin {
            androidContext(this@App)

            modules(
                listOf(
                    viewSettingModelModule,
                    viewSearchViewModel,
                    viewPlayerModelModule, dataSettingModule, domainSettingModule,
                    dataPlayerModule, domainPlayerModule,
                    domainSearchModule, dataSearchModule,
                )
            )
        }

        val interactor : SettingInteractor by inject()
        val darkTheme = interactor.defaultChange()
     //   switchTheme(darkTheme)
        interactor.changeTheme(darkTheme)
    }


//    private fun switchTheme(check: Boolean) {
//        interactor.changeTheme(check)
//    }
}