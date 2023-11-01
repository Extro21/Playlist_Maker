package com.practicum.playlistmarket.media.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.PlaylistItemBinding
import com.practicum.playlistmarket.media.domain.module.PlayList

class PlayListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val binding = PlaylistItemBinding.bind(view)

        fun bind(playList: PlayList) = with(binding) {
            val cornerSize = itemView.resources.getDimensionPixelSize(R.dimen.indent_image_playlist)
            Glide.with(itemView)
                .load(playList.uri)
                .centerCrop()
                .placeholder(R.drawable.image_shape_default_playlist)
                .transform(RoundedCorners(cornerSize))
                .into(imagePlayList)

            namePlaylist.text = playList.name
            countTrack.text = playList.quantityTracks.toString()
        }

}