package com.practicum.playlistmarket.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmarket.*
import com.practicum.playlistmarket.player.domain.models.Track


import kotlin.collections.ArrayList


class HistoryAdapter(private val clickListener: TrackClickListener) : RecyclerView.Adapter<TrackHolder>() {

    var trackListHistory = ArrayList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_track_item, parent, false)
        return TrackHolder(view)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(trackListHistory[position])
        holder.itemView.setOnClickListener {clickListener.onTrackClick(trackListHistory[position])
        }
    }

    override fun getItemCount(): Int = trackListHistory.size


}