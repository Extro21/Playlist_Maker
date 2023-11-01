package com.practicum.playlistmarket.player.ui

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.MediaPlayerPlaylistItemBinding
import com.practicum.playlistmarket.media.domain.module.PlayList

class PlayListPlayerHolder(view: View) : RecyclerView.ViewHolder(view) {

    private val binding = MediaPlayerPlaylistItemBinding.bind(view)

    fun bind(playList: PlayList) = with(binding){
        val cornerSize = itemView.resources.getDimensionPixelSize(R.dimen.corners_image_track)
        Glide.with(itemView)
            .load(playList.uri)
            .centerCrop()
            .placeholder(R.drawable.icon_track_default)
            .transform(RoundedCorners(cornerSize))
            .into(imageMusic)


        textPlayList.text = playList.name
      //  countTrack.text = playList.playListId
    }

}