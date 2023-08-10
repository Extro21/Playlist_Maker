package com.practicum.playlistmarket.main.ui

import android.app.Application
import android.content.Intent
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmarket.presentation.MediaActivity
import com.practicum.playlistmarket.search.ui.SearchActivity
import com.practicum.playlistmarket.settings.ui.SettingsActivity

class MainViewModel(application: Application) : AndroidViewModel(application) {


    private val openActivity = MutableLiveData<Intent>()
    val openActivityTo: LiveData<Intent> get() = openActivity

    fun openSearch() {
        val intent = Intent(getApplication(), SearchActivity::class.java)
       // intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        openActivity.value = intent
    }

    fun openPlayList() {
        val intent = Intent(getApplication(), MediaActivity::class.java)
          //  intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
        openActivity.value = intent
    }

//    fun openSetting() {
//        val intent = Intent(getApplication(), SettingsActivity::class.java)
//      //  intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
//        openActivity.value = intent
//    }


    companion object {
        fun getViewModelFactory(application: Application): ViewModelProvider.Factory = viewModelFactory {
                initializer {
                    MainViewModel(application)
                }
            }
    }
}