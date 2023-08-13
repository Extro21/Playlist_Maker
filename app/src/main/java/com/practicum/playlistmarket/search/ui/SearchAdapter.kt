package com.practicum.playlistmarket.search.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmarket.*
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.util.TrackClickListener


class SearchAdapter(private val clickListener: TrackClickListener) : RecyclerView.Adapter<TrackHolder>() {

   // val interactor = Creator.provideHistoryInteractor()

    var trackList = ArrayList<Track>()

//    private var isClickAllowed = true
//    private val handler = Handler(Looper.getMainLooper())


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_track_item, parent, false)
        return TrackHolder(view)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
//        val sharedPreferences = holder.itemView.context.getSharedPreferences(
//            HISTORY_TRACK,
//            AppCompatActivity.MODE_PRIVATE
//        )
        holder.bind(trackList[position])

        holder.itemView.setOnClickListener { clickListener.onTrackClick(trackList[position]) }

//        holder.itemView.setOnClickListener {
//
//            if (clickDebounce()) {
//                val json = Gson().toJson(trackList[position])
//                sharedPreferences.edit()
//                    .putString(KEY_HISTORY, json)
//                    .apply()
//
//                val intent = Intent(it.context, MediaPlayerActivity::class.java)
//                intent.putExtra(EXTRA_TRACK_NAME, trackList[position].trackName)
//                intent.putExtra(EXTRA_ARTIST_NAME, trackList[position].artistName)
//                intent.putExtra(EXTRA_TIME_MILLIS, trackList[position].trackTimeMillis)
//                intent.putExtra(EXTRA_IMAGE, trackList[position].artworkUrl100)
//                intent.putExtra(EXTRA_DATA, trackList[position].releaseDate)
//                intent.putExtra(EXTRA_COLLECTION_NAME, trackList[position].collectionName)
//                intent.putExtra(EXTRA_PRIMARY_NAME, trackList[position].primaryGenreName)
//                intent.putExtra(EXTRA_COUNTRY, trackList[position].country)
//                intent.putExtra(EXTRA_SONG, trackList[position].previewUrl)
//                it.context.startActivity(intent)
//            }
//
//        }
    }

    override fun getItemCount(): Int = trackList.size


//    fun interface TrackClickListener {
//        fun onTrackClick(track: Track)
//    }

//
//    private fun clickDebounce(): Boolean {
//        val current = isClickAllowed
//        if (isClickAllowed) {
//            isClickAllowed = false
//            handler.postDelayed({ isClickAllowed = true }, CLICK_DEBOUNCE_DELAY)
//        }
//        return current
//    }

//    companion object {
//        private const val CLICK_DEBOUNCE_DELAY = 1000L
//    }

}