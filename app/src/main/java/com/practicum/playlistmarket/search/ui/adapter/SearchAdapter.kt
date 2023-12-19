package com.practicum.playlistmarket.search.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.practicum.playlistmarket.*
import com.practicum.playlistmarket.player.domain.models.Track

class SearchAdapter(private val clickListener: TrackClickListener) :
    RecyclerView.Adapter<TrackHolder>() {

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

    fun updateData(newData: List<Track>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(trackList, newData))
        trackList.clear()
        trackList.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }

    fun clearData(newData: List<Track>) {
        val diffResult = DiffUtil.calculateDiff(DiffCallback(trackList, newData))
        trackList.clear()
        trackList.addAll(newData)
        diffResult.dispatchUpdatesTo(this)
    }
}

private class DiffCallback(
    private val oldList: List<Track>,
    private val newList: List<Track>
) : DiffUtil.Callback() {
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].trackId == newList[newItemPosition].trackId
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }
}
