package com.practicum.playlistmarket.main.ui

import android.app.Application
import android.content.Intent
import androidx.lifecycle.*
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.practicum.playlistmarket.media.ui.MediaActivity
import com.practicum.playlistmarket.search.ui.SearchActivity
import com.practicum.playlistmarket.settings.ui.SettingViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {


//    private val openActivity = MutableLiveData<Intent>()
//    val openActivityTo: LiveData<Intent> get() = openActivity
//
//    fun openSearch() {
//        val intent = Intent(getApplication(), SearchActivity::class.java)
//        openActivity.value = intent
//    }
//
//    fun openPlayList() {
//        val intent = Intent(getApplication(), MediaActivity::class.java)
//        openActivity.value = intent
//    }
//
//
//
//    companion object {
//        fun getViewModelFactory(application: Application): ViewModelProvider.Factory = viewModelFactory {
//                initializer {
//                   // val application = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
//                    MainViewModel(application)
//                }
//
//            }
//    }
}