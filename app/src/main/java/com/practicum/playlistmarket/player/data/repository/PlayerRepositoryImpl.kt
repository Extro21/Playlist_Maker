package com.practicum.playlistmarket.player.data.repository

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.practicum.playlistmarket.player.domain.api.TrackStateListener
import com.practicum.playlistmarket.player.domain.api.TrackTimeListener
import com.practicum.playlistmarket.player.domain.repository.PlayerRepository
import com.practicum.playlistmarket.util.StatePlayer
import java.text.SimpleDateFormat
import java.util.*

class PlayerRepositoryImpl(
    private var trackTimeListener: TrackTimeListener,
    private val trackStateListener: TrackStateListener,
) :
    PlayerRepository {

    val handler = Handler(Looper.getMainLooper())

    private var time = DEFAULT_TIME_TRACK
    override fun getTime(): String {
        return time
    }

    val mediaPlayer = MediaPlayer()
    private var playerState = StatePlayer.STATE_DEFAULT

    override fun preparePlayer(trackUrl: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(trackUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = StatePlayer.STATE_PREPARED
            trackStateListener.getState(playerState)
        }
        mediaPlayer.setOnCompletionListener {
            playerState = StatePlayer.STATE_PREPARED
            trackStateListener.getState(playerState)
            time = DEFAULT_TIME_TRACK
        }
    }

    override fun releasePlayer() {
        mediaPlayer.release()
    }

    override fun playbackControl() {
        when (playerState) {
            StatePlayer.STATE_PLAYING -> {
                pausePlayer()
            }
            StatePlayer.STATE_PREPARED, StatePlayer.STATE_PAUSED -> {
                startPlayer()
            }
            else -> {
                StatePlayer.STATE_DEFAULT
            }
        }
    }

    override fun startPlayer() {
        mediaPlayer.start()
        playerState = StatePlayer.STATE_PLAYING
        updateTime(time)
        trackStateListener.getState(playerState)
    }

    override fun pausePlayer() {
        mediaPlayer.pause()
        playerState = StatePlayer.STATE_PAUSED
        trackStateListener.getState(playerState)
    }


    override fun updateTime(time: String) {
        this.time = time
        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    if (playerState == StatePlayer.STATE_PLAYING) {
                        this@PlayerRepositoryImpl.time = SimpleDateFormat(
                            "mm:ss",
                            Locale.getDefault()
                        ).format(mediaPlayer.currentPosition)
                        trackTimeListener.onTimeChanged(this@PlayerRepositoryImpl.time)
                        handler.postDelayed(
                            this,
                            REFRESH_LIST_DELAY_MILLIS,
                        )
                    }
                }
            },
            REFRESH_LIST_DELAY_MILLIS
        )
    }

    companion object {
        private const val REFRESH_LIST_DELAY_MILLIS = 300L
        private const val DEFAULT_TIME_TRACK = "00:00"
    }


//    enum class State(val state: Int) {
//        STATE_DEFAULT(0),
//        STATE_PREPARED(1),
//        STATE_PLAYING(2),
//        STATE_PAUSED(3),
//    }

}