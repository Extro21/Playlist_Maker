package com.practicum.playlistmarket.media.ui.adapter.playlistViewAdapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.SearchTrackItemBinding
import com.practicum.playlistmarket.player.domain.models.Track
import java.text.SimpleDateFormat
import java.util.Locale

class PlayListViewViewHolder(val view : View) : RecyclerView.ViewHolder(view) {

    private val binding = SearchTrackItemBinding.bind(view)

    fun bind(track : Track) = with(binding){

        val cornerSize = itemView.resources.getDimensionPixelSize(R.dimen.corners_image_track)
        Glide.with(itemView)
            .load(track.artworkUrl100)
            .centerCrop()
            .placeholder(R.drawable.icon_track_default)
            .transform(RoundedCorners(cornerSize))
            .into(imageMusicSearch)

        textSong.text = track.trackName
        textGroup.text = track.artistName
        textTimeMusic.text = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track.trackTimeMillis?.toInt())


    }

}