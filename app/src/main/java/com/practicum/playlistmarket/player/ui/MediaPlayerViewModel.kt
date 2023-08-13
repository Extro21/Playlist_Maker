package com.practicum.playlistmarket.player.ui

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.practicum.playlistmarket.Creator.Creator
import com.practicum.playlistmarket.player.domain.api.TrackStateListener
import com.practicum.playlistmarket.player.domain.api.TrackTimeListener
import com.practicum.playlistmarket.util.StatePlayer

class MediaPlayerViewModel() : ViewModel(), TrackTimeListener, TrackStateListener {
    private val playerInteractor = Creator.providePlayerInteractor(this, this)
    val handler = Handler(Looper.getMainLooper())

    init {
        listenState()
    }

    var state = StatePlayer.STATE_DEFAULT

    private val _timeData = MutableLiveData<String>()
    val timeData: LiveData<String> = _timeData

    private val _checkState = MutableLiveData<StatePlayer>()
    val checkState: LiveData<StatePlayer> = _checkState



    fun preparePlayer(urlTrack: String) {
        playerInteractor.preparePlayer(urlTrack)
    }

    fun playStart() {
        playerInteractor.playbackControl()
      //  checkState(state)
    }

    fun onPause() {
        playerInteractor.pausePlayer()
        // binding.btPlay.setImageResource(R.drawable.bt_play)
    }

    override fun onCleared() {
        super.onCleared()
        playerInteractor.releasePlayer()
    }

//    fun onDestroy() {
//        playerInteractor.releasePlayer()
//    }

    override fun onTimeChanged(time: String) {
        _timeData.value = time
        //binding.timeLeft.text = time
    }

    override fun getState(state: StatePlayer) {
        this.state = state
    }

    fun checkState(state: StatePlayer) {
        when (state) {
            StatePlayer.STATE_PLAYING -> _checkState.value = StatePlayer.STATE_PLAYING//binding.btPlay.setImageResource(R.drawable.button_pauseb)
            StatePlayer.STATE_PAUSED -> _checkState.value = StatePlayer.STATE_PAUSED//binding.btPlay.setImageResource(R.drawable.bt_play)
            StatePlayer.STATE_DEFAULT-> _checkState.value = StatePlayer.STATE_DEFAULT
            StatePlayer.STATE_PREPARED -> {
                _checkState.value = StatePlayer.STATE_PREPARED
//                binding.btPlay.setImageResource(R.drawable.bt_play)
//                binding.timeLeft.text = DEFAULT_TIME_TRACK
            }
        }
    }

    private fun listenState() {
        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    checkState(state)
                    handler.postDelayed(
                        this,
                        REFRESH_STATE
                    )
                }
            },
            REFRESH_STATE
        )
    }

    companion object {
        private const val REFRESH_STATE = 100L
    }
}