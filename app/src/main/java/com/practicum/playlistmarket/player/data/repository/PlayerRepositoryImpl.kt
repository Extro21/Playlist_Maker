package com.practicum.playlistmarket.player.data.repository

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import android.util.Log

import com.practicum.playlistmarket.player.domain.repository.PlayerRepository
import com.practicum.playlistmarket.player.domain.StatePlayer
import com.practicum.playlistmarket.player.domain.api.PlayerListener
import java.text.SimpleDateFormat
import java.util.*

class PlayerRepositoryImpl : PlayerRepository {

    private var listener: PlayerListener? = null

    val handler = Handler(Looper.getMainLooper())

    private var time = DEFAULT_TIME_TRACK

    val mediaPlayer = MediaPlayer()
    private var playerState = StatePlayer.STATE_DEFAULT


    override fun preparePlayer(trackUrl: String) {
        mediaPlayer.reset()
        mediaPlayer.setDataSource(trackUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = StatePlayer.STATE_PREPARED
            listener?.onStateUpdate(playerState)
        }
        mediaPlayer.setOnCompletionListener {
            playerState = StatePlayer.STATE_PREPARED
            listener?.onStateUpdate(playerState)
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
                Log.e("mylogRep", playerState.toString())
            }
            StatePlayer.STATE_PREPARED, StatePlayer.STATE_PAUSED -> {
                startPlayer()
                Log.e("mylogRep", playerState.toString())
            }
            else -> {
                StatePlayer.STATE_DEFAULT
            }
        }
    }

    override fun startPlayer() {
        playerState = StatePlayer.STATE_PLAYING
        mediaPlayer.start()
        updateTime(time)
        Log.e("mylogRep", "updateTimePlayer")
        listener?.onStateUpdate(playerState)
    }

    override fun pausePlayer() {
        playerState = StatePlayer.STATE_PAUSED
        mediaPlayer.pause()
        listener?.onStateUpdate(playerState)
    }


    override fun updateTime(time: String) {
        Log.e("mylogRep", "updateTime")
        this.time = time

        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    if (playerState == StatePlayer.STATE_PLAYING) {
                        this@PlayerRepositoryImpl.time = SimpleDateFormat(
                            "mm:ss",
                            Locale.getDefault()
                        ).format(mediaPlayer.currentPosition)
                        listener?.onTimeUpdate(this@PlayerRepositoryImpl.time)
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


    override fun setupListener(listener: PlayerListener) {
        this.listener = listener
    }


    companion object {
        private const val REFRESH_LIST_DELAY_MILLIS = 100L
        private const val DEFAULT_TIME_TRACK = "00:00"

    }

}