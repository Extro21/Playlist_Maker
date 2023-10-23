package com.practicum.playlistmarket.player.ui.view_model

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.practicum.playlistmarket.media.domain.FavoriteListener
import com.practicum.playlistmarket.media.domain.db.FavoriteInteractor
import com.practicum.playlistmarket.player.domain.StatePlayer
import com.practicum.playlistmarket.player.domain.api.PlayerInteractor
import com.practicum.playlistmarket.player.domain.api.PlayerListener
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class MediaPlayerViewModel(
    private val playerInteractor: PlayerInteractor,
    private val favoriteInteractor: FavoriteInteractor
) : ViewModel(), PlayerListener, FavoriteListener {


    private var timerJob: Job? = null

    init {
        listen()
    }

    private val _secondCounter = MutableLiveData<String>()
    val secondCounter: LiveData<String> = _secondCounter

    private val _checkState = MutableLiveData<StatePlayer>()
    val checkState: LiveData<StatePlayer> = _checkState

    private val _timeSong = MutableLiveData<String>()
    val timeSong: LiveData<String> = _timeSong

    private val _timeSongSec = MutableLiveData<String>()
    val timeSongSec: LiveData<String> = _timeSongSec


    private val _dataSong = MutableLiveData<String>()
    val dataSong: LiveData<String> = _dataSong

    private val _coverArtwork = MutableLiveData<String>()
    val coverArtwork: LiveData<String> = _coverArtwork

    private val _likeState = MutableLiveData<Boolean>()
    val likeState : LiveData<Boolean> = _likeState

    suspend fun addTrackFavorite(track: Track){
        Log.e("LikeLike", "${track.isFavorite} ViewModel")
        favoriteInteractor.addTrackFavorite(track)

    }

    fun checkLike(trackId : String){
        viewModelScope.launch {
            _likeState.value = favoriteInteractor.checkLikeTrack(trackId)
        }
    }

    override fun onFavoriteUpdate(isLiked: Boolean) {
        _likeState.value = isLiked
        Log.e("LikedLog", isLiked.toString())
    }

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
        onTimeUpdate()
    }

    fun onPause() {
        playerInteractor.pausePlayer()
        //   Log.e("mylog", state.toString())
    }

    override fun onCleared() {
        super.onCleared()
        playerInteractor.releasePlayer()

    }

    private fun listen() {
        playerInteractor.setListener(this)
        favoriteInteractor.setListener(this)
    }

    private fun onTimeUpdate() {
        timerJob = viewModelScope.launch {
            while (_checkState.value == StatePlayer.STATE_PLAYING) {
                delay(UPDATE_TIME_FREQUENCY)
                _timeSongSec.value = playerInteractor.getTime()
                Log.e("TimeLog", "Время: ${playerInteractor.getTime()}")
            }
            if (_checkState.value == StatePlayer.STATE_PREPARED) {
                _timeSongSec.value = DEFAULT_TIME_TRACK

            }
        }

    }


    override fun onStateUpdate(state: StatePlayer) {
        _checkState.value = state
    }

    companion object {
        private const val DEFAULT_TIME_TRACK = "00:00"
        private const val UPDATE_TIME_FREQUENCY = 250L
    }


}