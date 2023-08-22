package com.practicum.playlistmarket.settings.ui.view_model

import android.app.Application
import androidx.lifecycle.*
import com.practicum.playlistmarket.settings.domain.api.SettingInteractor


class SettingViewModel(application: Application,
                       private val settingInteractor : SettingInteractor)
    : AndroidViewModel(application) {


    fun sentSupport() {
        settingInteractor.openSupport()
    }

    fun shareApp(link: String) {
        settingInteractor.sharingApp(link)
    }

    fun termOfUse(){
        settingInteractor.openTermsOfUse()
    }


    fun default() : Boolean{
        return settingInteractor.defaultChange()
    }

    fun switchTheme(check: Boolean) {
        settingInteractor.changeTheme(check)
    }

}

