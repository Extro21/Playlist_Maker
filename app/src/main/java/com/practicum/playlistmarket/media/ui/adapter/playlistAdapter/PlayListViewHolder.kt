package com.practicum.playlistmarket.media.ui.adapter.playlistAdapter

import android.annotation.SuppressLint
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.practicum.playlistmarket.R
import com.practicum.playlistmarket.databinding.PlaylistItemBinding
import com.practicum.playlistmarket.media.domain.module.PlayList

class PlayListViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    private val binding = PlaylistItemBinding.bind(view)

    @SuppressLint("SetTextI18n")
    fun bind(playList: PlayList) = with(binding) {
        val cornerSize = itemView.resources.getDimensionPixelSize(R.dimen.indent_image_playlist)
        Glide.with(itemView)
            .load(playList.uri)
            .centerCrop()
            .placeholder(R.drawable.image_shape_default_playlist)
            .transform(RoundedCorners(cornerSize))
            .into(imagePlayList)

        val count = playList.quantityTracks


        namePlaylist.text = playList.name
        countTrack.text = playList.quantityTracks.toString() + " " + getEnding(count)
    }



   private fun getEnding(number: Int): String {
        val lastDigit = number % 10
        val lastTwoDigits = number % 100

        return when {
            lastTwoDigits in 11..19 -> view.context.getString(R.string.trackov)
            lastDigit == 1 -> view.context.getString(R.string.track)
            lastDigit in 2..4 -> view.context.getString(R.string.tracka)
            else -> view.context.getString(R.string.trackov)
        }
    }

}