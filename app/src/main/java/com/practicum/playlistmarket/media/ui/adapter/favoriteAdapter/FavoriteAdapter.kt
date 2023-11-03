package com.practicum.playlistmarket.media.ui.adapter.favoriteAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.search.ui.adapter.TrackClickListener

class FavoriteAdapter(private val clickListener: TrackClickListener) : RecyclerView.Adapter<FavoriteViewHolder>() {

    var trackListFavorite = ArrayList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_track_item, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(trackListFavorite[position])
        holder.itemView.setOnClickListener {clickListener.onTrackClick(trackListFavorite[position])
        }
    }

    override fun getItemCount(): Int = trackListFavorite.size
}