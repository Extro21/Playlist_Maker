package com.practicum.playlistmarket.media.ui.adapter.playlistViewAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.media.ui.adapter.TrackClickListenerLong
import com.practicum.playlistmarket.player.domain.models.Track


class PlayListViewAdapter(private val clickListener: TrackClickListenerLong) :
    RecyclerView.Adapter<PlayListViewViewHolder>() {

    var tracks = ArrayList<Track>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_track_item, parent, false)
        return PlayListViewViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlayListViewViewHolder, position: Int) {
        holder.bind(tracks[position])
        holder.itemView.setOnClickListener { clickListener.onTrackClick(tracks[position]) }
        holder.itemView.setOnLongClickListener {
            clickListener.onItemLongClick(tracks[position].trackId)
            true
        }
    }

    override fun getItemCount(): Int = tracks.size
}