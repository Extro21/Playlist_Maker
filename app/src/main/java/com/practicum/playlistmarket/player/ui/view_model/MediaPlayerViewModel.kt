package com.practicum.playlistmarket.player.ui.view_model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmarket.player.domain.api.TrackStateListener
import com.practicum.playlistmarket.player.domain.api.TrackTimeListener
import com.practicum.playlistmarket.player.domain.StatePlayer
import com.practicum.playlistmarket.player.domain.api.PlayerInteractor
import com.practicum.playlistmarket.player.domain.api.PlayerListener
import java.text.SimpleDateFormat
import java.util.*

class MediaPlayerViewModel(private val playerInteractor : PlayerInteractor
                          ) : ViewModel() , PlayerListener  {


  // private val playerInteractor1 = Creator.providePlayerInteractor(this, this)

    init{
        playerInteractor.setListenerToPlayer(this)
    }

    private val _secondCounter = MutableLiveData<String>()
    val secondCounter: LiveData<String> = _secondCounter

    private val _checkState = MutableLiveData<StatePlayer>()
    val checkState: LiveData<StatePlayer> = _checkState

    private val _timeSong = MutableLiveData<String>()
    val timeSong: LiveData<String> = _timeSong

    private val _dataSong = MutableLiveData<String>()
    val dataSong: LiveData<String> = _dataSong

    private val _coverArtwork = MutableLiveData<String>()
    val coverArtwork: LiveData<String> = _coverArtwork


    fun getCoverArtwork(urlImage: String?) {
        _coverArtwork.value = urlImage?.replaceAfterLast('/', "512x512bb.jpg")
    }

    fun correctDataSong(data: String) {
        _dataSong.value = data.substring(0, 4)
    }

    fun correctTimeSong(time: String?) {
        _timeSong.value = SimpleDateFormat("mm:ss", Locale.getDefault()).format(time?.toInt())
    }



    fun preparePlayer(urlTrack: String) {
        playerInteractor.preparePlayer(urlTrack)
      //  Log.e("mylog", state.toString())
    }

    fun playStart() {
        playerInteractor.playbackControl()

    }

    fun onPause() {
        playerInteractor.pausePlayer()
     //   Log.e("mylog", state.toString())
    }

    override fun onCleared() {
        super.onCleared()
        playerInteractor.releasePlayer()
    }

//    override fun onTimeChanged(time: String) {
//        _secondCounter.value = time
//    }
//
//    override fun getState(state: StatePlayer) {
//      //  this.state = state
//        _checkState.value = state
//    }

//    fun onTimeChanged() {
//
//      //  _secondCounter.value = trackTimeListener.onTimeChanged()
//
//    }

    override fun onTimeUpdate(time : String) {
        _secondCounter.value = time
    }

//    fun getState(state: StatePlayer) {
//        //  this.state = state
//        _checkState.value = trackStateListener.getState(state)
//    }



    companion object {
        private const val REFRESH_STATE = 1L
    }


}