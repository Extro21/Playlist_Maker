package com.practicum.playlistmarket

import android.app.Application
import com.practicum.playlistmarket.Creator.Creator
import com.practicum.playlistmarket.settings.domain.api.SettingInteractor

class App : Application() {

    private lateinit var interactor : SettingInteractor

    override fun onCreate() {
        super.onCreate()

        interactor = Creator.provideSettingInteractor(applicationContext)
        val darkTheme = interactor.defaultChange()
        switchTheme(darkTheme)
    }


    private fun switchTheme(check: Boolean) {
        interactor.changeTheme(check)
    }
}