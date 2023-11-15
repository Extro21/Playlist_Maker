package com.practicum.playlistmarket.player.ui.activity

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.ActivityMediaPlayerBinding
import com.practicum.playlistmarket.player.ui.view_model.MediaPlayerViewModel
import com.practicum.playlistmarket.player.domain.StatePlayer
import com.practicum.playlistmarket.player.domain.StatePlayer.*
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.launch
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
const val EXTRA_LIKE = "track_like"
const val EXTRA_ID = "track_id"
const val EXTRA_TRACK = "track_track"


class MediaPlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMediaPlayerBinding

    private var songUrl: String = ""

    private var isLiked: Boolean = false

    private val viewModel: MediaPlayerViewModel by viewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val track = intent.getParcelableExtra<Track>(EXTRA_TRACK)

        val trackID = intent.getStringExtra(EXTRA_ID)
        Log.e("qazwsx", trackID.toString())

        if (track != null) {
            viewModel.checkLike(track.trackId)
        }

        songUrl = intent.getStringExtra(EXTRA_SONG).toString()

        viewModel.preparePlayer(songUrl)

        binding.btPlay.setOnClickListener {
            Log.e("TimeLog", "PlayerStart")
            viewModel.playStart()
        }

        viewModel.timeSongSec.observe(this) {
            binding.timeLeft.text = it
            Log.e("TimeLog", "Активити $it")
        }


        viewModel.checkState.observe(this) {
            checkState(it)
        }

        binding.apply {
            trackName.text = intent.getStringExtra(EXTRA_TRACK_NAME)
            groupName.text = intent.getStringExtra(EXTRA_ARTIST_NAME)
            countryApp.text = intent.getStringExtra(EXTRA_COUNTRY)
            isLiked = intent.getBooleanExtra(EXTRA_LIKE, false)

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
        viewModel.correctDataSong(data)
        viewModel.dataSong.observe(this) { data ->
            binding.yearApp.text = data
        }

        val time = intent.getStringExtra(EXTRA_TIME_MILLIS)

        if (time != null) {
            viewModel.correctTimeSong(time)

            viewModel.timeSong.observe(this) { timeSong ->
                binding.durationApp.text = timeSong
            }
        }

        val urlImage = intent.getStringExtra(EXTRA_IMAGE)
        viewModel.getCoverArtwork(urlImage)

        viewModel.coverArtwork.observe(this) { urlSong ->
            var url = urlSong

            val cornerSize = resources.getDimensionPixelSize(R.dimen.corners_image_track)
            Glide.with(this)
                .load(url)
                .centerCrop()
                .placeholder(R.drawable.icon_track_default)
                .transform(RoundedCorners(cornerSize))
                .into(binding.trackImage)
        }

        viewModel.secondCounter.observe(this) { time ->
            binding.timeLeft.text = time
        }

        binding.btLike.setOnClickListener {
            lifecycleScope.launch {
                if (track != null) {
                    Log.e("LikeLike", "${track.isFavorite.toString()} Activity, $isLiked ")
                    viewModel.addTrackFavorite(track)
                }
            }
        }

        viewModel.likeState.observe(this) { isLiked ->
            if (isLiked) {
                binding.btLike.setImageResource(R.drawable.button_like_true)
                track!!.isFavorite = isLiked
            } else {
                binding.btLike.setImageResource(R.drawable.button__like)
                track!!.isFavorite = isLiked
            }
        }


    }


    override fun onPause() {
        super.onPause()
        viewModel.onPause()
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
            }
        }
    }


}