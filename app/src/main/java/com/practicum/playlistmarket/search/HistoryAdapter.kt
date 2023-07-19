package com.practicum.playlistmarket.search

import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmarket.*
import kotlin.collections.ArrayList


class HistoryAdapter : RecyclerView.Adapter<TrackHolder>() {

    var trackListHistory = ArrayList<Track>()
    private var isClickAllowed = true
    private val handler = Handler(Looper.getMainLooper())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_track_item, parent, false)
        return TrackHolder(view)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(trackListHistory[position])

        holder.itemView.setOnClickListener {

            if (clickDebounce()) {
                val intent = Intent(it.context, MediaPlayerActivity::class.java)
                intent.putExtra(EXTRA_TRACK_NAME, trackListHistory[position].trackName)
                intent.putExtra(EXTRA_ARTIST_NAME, trackListHistory[position].artistName)
                intent.putExtra(EXTRA_TIME_MILLIS, trackListHistory[position].trackTimeMillis)
                intent.putExtra(EXTRA_IMAGE, trackListHistory[position].artworkUrl100)
                intent.putExtra(EXTRA_DATA, trackListHistory[position].releaseDate)
                intent.putExtra(EXTRA_COLLECTION_NAME, trackListHistory[position].collectionName)
                intent.putExtra(EXTRA_PRIMARY_NAME, trackListHistory[position].primaryGenreName)
                intent.putExtra(EXTRA_COUNTRY, trackListHistory[position].country)
                intent.putExtra(EXTRA_SONG, trackListHistory[position].previewUrl)
                it.context.startActivity(intent)

            }
        }
    }

    override fun getItemCount(): Int = trackListHistory.size

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
        }
        return current
    }

    companion object {
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

}