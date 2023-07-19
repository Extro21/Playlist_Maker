package com.practicum.playlistmarket

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmarket.databinding.ActivityMediaPlayerBinding
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


class MediaPlayerActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMediaPlayerBinding
    private lateinit var songUrl: String
    private var mediaPlayer = MediaPlayer()
    val handler = Handler(Looper.getMainLooper())

    private var playerState = STATE_DEFAULT


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)


        songUrl = intent.getStringExtra(EXTRA_SONG).toString()
        preparePlayer()

        binding.btPlay.setOnClickListener {
            playbackControl()
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

        val cornerSize = resources.getDimensionPixelSize(R.dimen.corners_image_track)

        fun getCoverArtwork() =
            intent.getStringExtra(EXTRA_IMAGE)?.replaceAfterLast('/', "512x512bb.jpg")


        Glide.with(this)
            .load(getCoverArtwork())
            .centerCrop()
            .placeholder(R.drawable.icon_track_default)
            .transform(RoundedCorners(cornerSize))
            .into(binding.trackImage)


    }

    private fun playbackControl() {
        when (playerState) {
            STATE_PLAYING -> {
                pausePlayer()
            }
            STATE_PREPARED, STATE_PAUSED -> {
                startPlayer()
            }
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        binding.btPlay.setImageResource(R.drawable.button_pauseb)
        playerState = STATE_PLAYING
        timeSound()
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        binding.btPlay.setImageResource(R.drawable.bt_play)
        playerState = STATE_PAUSED

    }

    private fun preparePlayer() {
        mediaPlayer.setDataSource(songUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            binding.btPlay.isEnabled = true
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            binding.btPlay.setImageResource(R.drawable.bt_play)
            playerState = STATE_PREPARED
            binding.timeLeft.text = DEFAULT_TIME_TRACK


        }
    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    private fun timeSound() {
        handler.postDelayed(
            object : Runnable {
                override fun run() {
                    if (playerState == STATE_PLAYING) {
                        binding.timeLeft.text = SimpleDateFormat(
                            "mm:ss",
                            Locale.getDefault()
                        ).format(mediaPlayer.currentPosition)

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
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3
        private const val REFRESH_LIST_DELAY_MILLIS = 300L
        private const val DEFAULT_TIME_TRACK = "00:00"
    }

}