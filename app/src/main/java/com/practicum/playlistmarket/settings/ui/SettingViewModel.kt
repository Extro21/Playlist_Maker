package com.practicum.playlistmarket.settings.ui

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmarket.Creator.Creator


class SettingViewModel(application: Application) : AndroidViewModel(application) {

    private val settingInteractor = Creator.provideSettingInteractor(application)



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

    companion object {
        fun getViewModelFactory() :ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application = this[APPLICATION_KEY]
                SettingViewModel(application!!)
            }
        }
    }
}

