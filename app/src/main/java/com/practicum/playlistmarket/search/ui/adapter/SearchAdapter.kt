package com.practicum.playlistmarket.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmarket.*
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.util.TrackClickListener


class SearchAdapter(private val clickListener: TrackClickListener) : RecyclerView.Adapter<TrackHolder>() {
    

    var trackList = ArrayList<Track>()




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_track_item, parent, false)
        return TrackHolder(view)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(trackList[position])

        holder.itemView.setOnClickListener { clickListener.onTrackClick(trackList[position]) }

    }

    override fun getItemCount(): Int = trackList.size


}