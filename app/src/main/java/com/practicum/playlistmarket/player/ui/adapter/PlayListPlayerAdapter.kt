package com.practicum.playlistmarket.player.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.media.ui.adapter.playlistAdapter.PlayListClickListener

class PlayListPlayerAdapter(private val playListClickListener: PlayListClickListener) : RecyclerView.Adapter<PlayListPlayerHolder>()  {

    var playList = ArrayList<PlayList>()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlayListPlayerHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.media_player_playlist_item,parent, false)
        return PlayListPlayerHolder(view)
    }

    override fun onBindViewHolder(holder: PlayListPlayerHolder, position: Int) {
        holder.bind(playList = playList[position])
        holder.itemView.setOnClickListener {playListClickListener.onPlayListClick(playList[position]) }
    }

    override fun getItemCount(): Int = playList.size


}