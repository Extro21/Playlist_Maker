package com.practicum.playlistmarket.player.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.ActivityMediaPlayerBinding
import com.practicum.playlistmarket.util.StatePlayer
import com.practicum.playlistmarket.util.StatePlayer.*
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


class MediaPlayerActivity : ComponentActivity() {


    var state = STATE_DEFAULT
    private lateinit var binding: ActivityMediaPlayerBinding

    private lateinit var viewModel: MediaPlayerViewModel

    private var songUrl: String = ""



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        songUrl = intent.getStringExtra(EXTRA_SONG).toString()

        viewModel = ViewModelProvider(this)[MediaPlayerViewModel::class.java]

        viewModel.preparePlayer(songUrl)

        //  listenState()

        binding.btPlay.setOnClickListener {
            viewModel.playStart()
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

        viewModel.timeData.observe(this) { time ->
            binding.timeLeft.text = time
        }

        viewModel.checkState.observe(this) {
            checkState(it)
        }

    }

    override fun onPause() {
        super.onPause()
        viewModel.onPause()
        binding.btPlay.setImageResource(R.drawable.bt_play)
        //btEnabled = false
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

//    override fun onTimeChanged(time: String) {
//        binding.timeLeft.text = time
//    }
//
//    override fun getState(state: StatePlayer) {
//        this.state = state
//    }

    fun checkState(state: StatePlayer) {
        when (state) {
            STATE_PLAYING -> binding.btPlay.setImageResource(R.drawable.button_pauseb)
            STATE_PAUSED, STATE_DEFAULT -> binding.btPlay.setImageResource(R.drawable.bt_play)
            STATE_PREPARED -> {
                binding.btPlay.setImageResource(R.drawable.bt_play)
                binding.timeLeft.text = DEFAULT_TIME_TRACK
            }
        }
    }
//
//    private fun listenState() {
//        handler.postDelayed(
//            object : Runnable {
//                override fun run() {
//                    checkState(state)
//                    handler.postDelayed(
//                        this,
//                        REFRESH_STATE
//                    )
//                }
//            },
//            REFRESH_STATE
//        )
//    }

    companion object {
        private const val REFRESH_STATE = 100L
        private const val DEFAULT_TIME_TRACK = "00:00"
    }
}