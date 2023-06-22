package com.practicum.playlistmarket

import android.os.Bundle
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


class MediaPlayerActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMediaPlayerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMediaPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

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
}