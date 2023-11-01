package com.practicum.playlistmarket.media.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.media.domain.module.PlayList

class PlayListAdapter(private val clickListener: PlayListClickListener) : RecyclerView.Adapter<PlayListViewHolder>() {

    var playList = ArrayList<PlayList>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.playlist_item, parent, false)
        return PlayListViewHolder(view)
    }

    override fun getItemCount(): Int  = playList.size

    override fun onBindViewHolder(holder: PlayListViewHolder, position: Int) {
        holder.bind(playList = playList[position])
        holder.itemView.setOnClickListener {clickListener.onPlayListClick(playList[position])
        }
    }


}