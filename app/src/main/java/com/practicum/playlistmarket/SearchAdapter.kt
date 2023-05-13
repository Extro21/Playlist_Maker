package com.practicum.playlistmarket

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmarket.databinding.SearchTrackItemBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners

class SearchAdapter : RecyclerView.Adapter<SearchAdapter.SearchHolder>() {


    private val trackList = ArrayList<Track>()


    class SearchHolder(item: View) : RecyclerView.ViewHolder(item) {
        private val binding = SearchTrackItemBinding.bind(item)
        fun bind(track: Track) = with(binding) {
            val cornerSize = itemView.resources.getDimensionPixelSize(R.dimen.corners_image_track)
            Glide.with(itemView)
                .load(track.artworkUrl100)
                .centerCrop()
                .placeholder(R.drawable.icon_treck_default)
                .transform(RoundedCorners(cornerSize))
                .into(imageMusicSearch)

            textGroup.text = track.artistName
            textTimeMusic.text = track.trackTime
            textSong.text = track.trackName
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_track_item, parent, false)
        return SearchHolder(view)
    }

    override fun onBindViewHolder(holder: SearchHolder, position: Int) {
        holder.bind(trackList[position])
    }

    override fun getItemCount(): Int {
        return trackList.size
    }

    fun addMusicAllList(tracks: ArrayList<Track>) {
        trackList.addAll(tracks)
    }


}