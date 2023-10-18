package com.practicum.playlistmarket.media.ui.adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.SearchTrackItemBinding
import com.practicum.playlistmarket.player.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class FavoriteViewHolder(item : View) : RecyclerView.ViewHolder(item) {

    private val binding = SearchTrackItemBinding.bind(item)

    fun bind(track: Track) = with(binding){
        val cornerSize = itemView.resources.getDimensionPixelSize(R.dimen.corners_image_track)
        Glide.with(itemView)
            .load(track.artworkUrl100)
            .centerCrop()
            .placeholder(R.drawable.icon_track_default)
            .transform(RoundedCorners(cornerSize))
            .into(imageMusicSearch)

        textGroup.text = track.artistName
        textTimeMusic.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis?.toInt())
        textSong.text = track.trackName

    }

}