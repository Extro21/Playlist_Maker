package com.practicum.playlistmarket.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import kotlin.collections.ArrayList
import com.google.gson.Gson
import com.practicum.playlistmarket.*


class SearchAdapter : RecyclerView.Adapter<TrackHolder>() {

    var trackList = ArrayList<Track>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.search_track_item, parent, false)
        return TrackHolder(view)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        val sharedPreferences = holder.itemView.context.getSharedPreferences(
            HISTORY_TRACK,
            AppCompatActivity.MODE_PRIVATE
        )
        holder.bind(trackList[position])
        holder.itemView.setOnClickListener {
            val json = Gson().toJson(trackList[position])
            sharedPreferences.edit()
                .putString(KEY_HISTORY, json)
                .apply()
        }
    }

    override fun getItemCount(): Int = trackList.size


}