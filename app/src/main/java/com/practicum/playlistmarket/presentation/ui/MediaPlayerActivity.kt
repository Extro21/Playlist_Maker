package com.practicum.playlistmarket.presentation.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmarket.Creator.Creator
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.ActivityMediaPlayerBinding
import com.practicum.playlistmarket.domain.api.TrackStateListener
import com.practicum.playlistmarket.domain.api.TrackTimeListener
import com.practicum.playlistmarket.presentation.StatePlayer.*
import java.text.SimpleDateFormat
import java.util.*

const val EXTRA_TRACK_NAME = "track_name"
const val EXTRA_ARTIST_NAME = "artist_name"
const val EXTRA_TIME_MILLIS = "time_millis"
const val EXTRA_IMAGE = "track_Image"
const val EXTRA_COUNTRY = "track_country"
const val EXTRA_DATA = "track_data"
const val EXTRA_PRIMARY_NAME = "track_primary_name"
const val EXTRA_COLLECTION_NAME = "track_collection_name"
const val EXTRA_SONG = "track_song"


class MediaPlayerActivity : AppCompatActivity(), TrackTimeListener, TrackStateListener {

    private val playerInteractor = Creator.providePlayerInteractor(this, this)
    val handler = Handler(Looper.getMainLooper())
    var state = STATE_DEFAULT.state
    private lateinit var binding: ActivityMediaPlayerBinding
    private lateinit var songUrl: String

    var btEnabled = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        songUrl = intent.getStringExtra(EXTRA_SONG).toString()
        playerInteractor.preparePlayer(songUrl)

//
//        val timeInteractor = playerRepository.
//
////        timeInteractor.setOnTimeListener { time ->
////            onTimeChanged(time)
////        }
//
        listenState()

        binding.btPlay.setOnClickListener {
            playerInteractor.playbackControl()
            checkState(state)

        }


        binding.apply {
            trackName.text = intent.getStringExtra(EXTRA_TRACK_NAME)
            groupName.text = intent.getStringExtra(EXTRA_ARTIST_NAME)
            countryApp.text = intent.getStringExtra(EXTRA_COUNTRY)

            val albumText = intent.getStringExtra(EXTRA_COLLECTION_NAME)
            if (albumText != null) {
                albumApp.text = albumText
            } else {
                albumApp.visibility = View.GONE
                album.visibility = View.GONE
            }

            genreApp.text = intent.getStringExtra(EXTRA_PRIMARY_NAME)
        }

        binding.toolbar.setOnClickListener {
            finish()
        }


        val data = intent.getStringExtra(EXTRA_DATA).toString()
        val dataOnlyYear = data.substring(0, 4)
        binding.yearApp.text = dataOnlyYear


        val time = intent.getStringExtra(EXTRA_TIME_MILLIS)
        if (time != null) {
            binding.durationApp.text =
                SimpleDateFormat("mm:ss", Locale.getDefault()).format(time.toInt())
        }

        fun getCoverArtwork() =
            intent.getStringExtra(EXTRA_IMAGE)?.replaceAfterLast('/', "512x512bb.jpg")

        val cornerSize = resources.getDimensionPixelSize(R.dimen.corners_image_track)
        Glide.with(this)
            .load(getCoverArtwork())
            .centerCrop()
            .placeholder(R.drawable.icon_track_default)
            .transform(RoundedCorners(cornerSize))
            .into(binding.trackImage)

    }


    override fun onPause() {
        super.onPause()
        playerInteractor.pausePlayer()
        binding.btPlay.setImageResource(R.drawable.bt_play)
        btEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        playerInteractor.releasePlayer()

    }

    override fun onTimeChanged(time: String) {
        binding.timeLeft.text = time

    }

    override fun getState(state: Int) {
        this.state = state
    }


    fun checkState(state : Int){
        when(state){
           STATE_PLAYING.state ->  binding.btPlay.setImageResource(R.drawable.button_pauseb)
            STATE_PAUSED.state, STATE_DEFAULT.state ->  binding.btPlay.setImageResource(R.drawable.bt_play)
            STATE_PREPARED.state -> {
                binding.btPlay.setImageResource(R.drawable.bt_play)
                binding.timeLeft.text = DEFAULT_TIME_TRACK
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
        private const val DEFAULT_TIME_TRACK = "00:00"
    }

}