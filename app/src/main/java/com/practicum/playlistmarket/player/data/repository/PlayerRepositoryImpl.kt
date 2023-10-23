package com.practicum.playlistmarket.player.data.repository

import android.media.MediaPlayer
import android.util.Log
import com.practicum.playlistmarket.player.domain.repository.PlayerRepository
import com.practicum.playlistmarket.player.domain.StatePlayer
import com.practicum.playlistmarket.player.domain.api.PlayerListener
import java.text.SimpleDateFormat
import java.util.*

class PlayerRepositoryImpl : PlayerRepository {

    private var listener: PlayerListener? = null

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
            //  time = DEFAULT_TIME_TRACK
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
        Log.e("mylogRep", "updateTimePlayer")
       listener?.onStateUpdate(playerState)
    }

    override fun pausePlayer() {
        playerState = StatePlayer.STATE_PAUSED
        mediaPlayer.pause()
        listener?.onStateUpdate(playerState)
    }


    override fun getTime(): String {
        time = getCurrentPlayerPosition()
        return time
    }

    /** Весь код, связанный с форматированием времени и дат вынести в
    отдельный класс-утилиту object DateTimeUtil. Это позволяет
    избавиться от дублирования кода, так же удобно следить за всеми
    форматами дат и времени в проекте. Ещё этот класс будет легко перенести в
    другой проект.*/
    private fun getCurrentPlayerPosition(): String {
        return SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
            ?: DEFAULT_TIME_TRACK
    }

    override fun setupListener(listener: PlayerListener) {
        this.listener = listener
    }


    companion object {
        private const val DEFAULT_TIME_TRACK = "00:00"

    }


}