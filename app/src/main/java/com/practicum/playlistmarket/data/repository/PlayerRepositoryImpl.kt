package com.practicum.playlistmarket.data.repository

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.practicum.playlistmarket.domain.api.TrackStateListener
import com.practicum.playlistmarket.domain.api.TrackTimeListener
import com.practicum.playlistmarket.domain.repository.PlayerRepository
import java.text.SimpleDateFormat
import java.util.*

class PlayerRepositoryImpl(private var trackTimeListener: TrackTimeListener, private val trackStateListener: TrackStateListener) : PlayerRepository {

    val handler = Handler(Looper.getMainLooper())

    var checkEnded: Boolean = false
    var checkReady = false
    private var time = DEFAULT_TIME_TRACK
    override fun getTime(): String {
        return time
    }

    val mediaPlayer = MediaPlayer()
    private var playerState = State.STATE_DEFAULT.state

    override fun preparePlayer(trackUrl: String) {
        mediaPlayer.setDataSource(trackUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = State.STATE_PREPARED.state
            trackStateListener.getState(playerState)
            checkReady = true
        }
        mediaPlayer.setOnCompletionListener {
            playerState = State.STATE_PREPARED.state
            trackStateListener.getState(playerState)
            time = DEFAULT_TIME_TRACK
            checkEnded = true
        }
    }

    override fun releasePlayer() {
        mediaPlayer.release()
    }

    override fun playbackControl() {
        when (playerState) {
            State.STATE_PLAYING.state -> {
                pausePlayer()
            }
            State.STATE_PREPARED.state, State.STATE_PAUSED.state -> {
                startPlayer()
            }
            else -> {
                State.STATE_DEFAULT
            }
        }
    }

    override fun startPlayer() {
        mediaPlayer.start()
        playerState = State.STATE_PLAYING.state
        updateTime(time)
        trackStateListener.getState(playerState)
    }

    override fun pausePlayer() {
        mediaPlayer.pause()
        playerState = State.STATE_PAUSED.state
        trackStateListener.getState(playerState)
    }


    override fun updateTime(time : String) {
        this.time = time
        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    if (playerState == State.STATE_PLAYING.state) {
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


    enum class State(val state: Int) {
        STATE_DEFAULT(0),
        STATE_PREPARED(1),
        STATE_PLAYING(2),
        STATE_PAUSED(3),
    }

}