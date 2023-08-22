package com.practicum.playlistmarket.player.ui.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.ActivityMediaPlayerBinding
import com.practicum.playlistmarket.player.ui.view_model.MediaPlayerViewModel
import com.practicum.playlistmarket.player.domain.StatePlayer
import com.practicum.playlistmarket.player.domain.StatePlayer.*
import org.koin.androidx.viewmodel.ext.android.viewModel


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

    private var songUrl: String = ""

    private val vm : MediaPlayerViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        songUrl = intent.getStringExtra(EXTRA_SONG).toString()

        vm.preparePlayer(songUrl)


        binding.btPlay.setOnClickListener {
            vm.playStart()
        }

        vm.checkState.observe(this) {
            checkState(it)
            Log.e("mylogPlay", it.toString())

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
        vm.correctDataSong(data)
        vm.dataSong.observe(this) {
            binding.yearApp.text = it
        }


        val time = intent.getStringExtra(EXTRA_TIME_MILLIS)

        if (time != null) {
            vm.correctTimeSong(time)

            vm.timeSong.observe(this) {
                binding.durationApp.text = it
            }
        }

        val urlImage = intent.getStringExtra(EXTRA_IMAGE)
        vm.getCoverArtwork(urlImage)

        vm.coverArtwork.observe(this) {
            var url = it

            val cornerSize = resources.getDimensionPixelSize(R.dimen.corners_image_track)
            Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.icon_track_default)
                .transform(RoundedCorners(cornerSize))
                .into(binding.trackImage)
        }

        vm.secondCounter.observe(this) { time ->
            binding.timeLeft.text = time
        }
    }

    override fun onPause() {
        super.onPause()
        vm.onPause()
    }

    private fun checkState(state: StatePlayer) {
        when (state) {
            STATE_PLAYING -> {
                binding.btPlay.setImageResource(R.drawable.button_pauseb)
                Log.e("mylogPlayningState", state.toString())
            }
            STATE_PAUSED, STATE_DEFAULT -> {
                binding.btPlay.setImageResource(R.drawable.bt_play)
                Log.e("mylogPauseStaytrning", state.toString())
            }
            STATE_PREPARED -> {
                binding.btPlay.setImageResource(R.drawable.bt_play)
                binding.timeLeft.text = DEFAULT_TIME_TRACK
            }
        }
    }

    companion object {
        private const val DEFAULT_TIME_TRACK = "00:00"
    }
}