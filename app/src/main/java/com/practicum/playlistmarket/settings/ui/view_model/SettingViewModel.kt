package com.practicum.playlistmarket.settings.ui.view_model

import android.app.Application
import androidx.lifecycle.*
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmarket.Creator.Creator
import com.practicum.playlistmarket.settings.domain.api.SettingInteractor
import org.koin.java.KoinJavaComponent.inject


class SettingViewModel(application: Application,
                       private val settingInteractor : SettingInteractor)
    : AndroidViewModel(application) {

  //  private val settingInteractor = Creator.provideSettingInteractor(application)

    //private val settingInteractor : SettingInteractor by inject()


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

//    companion object {
//        fun getViewModelFactory() :ViewModelProvider.Factory = viewModelFactory {
//            initializer {
//                val application = this[APPLICATION_KEY]
//                SettingViewModel(application!!)
//            }
//        }
//    }
}

