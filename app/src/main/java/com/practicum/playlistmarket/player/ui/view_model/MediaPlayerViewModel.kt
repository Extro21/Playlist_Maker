package com.practicum.playlistmarket.player.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmarket.player.domain.StatePlayer
import com.practicum.playlistmarket.player.domain.api.PlayerInteractor
import com.practicum.playlistmarket.player.domain.api.PlayerListener
import java.text.SimpleDateFormat
import java.util.*

class MediaPlayerViewModel(
    private val playerInteractor: PlayerInteractor
) : ViewModel(), PlayerListener {


    init {
        listen()
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
        Log.e("TimeLog", "PlayerStart")
    }

    fun onPause() {
        playerInteractor.pausePlayer()
        //   Log.e("mylog", state.toString())
    }

    override fun onCleared() {
        super.onCleared()
        playerInteractor.releasePlayer()
    }

   private fun listen(){
        playerInteractor.setListener(this)
    }

    override fun onStateUpdate(state: StatePlayer) {
        _checkState.value = state
    }

    override fun onTimeUpdate(time: String) {
        Log.e("TimeLog", time)
        _secondCounter.value = time
    }


}