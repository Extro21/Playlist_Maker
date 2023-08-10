package com.practicum.playlistmarket.presentation.ui

import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.search.ui.HistoryAdapter
import com.practicum.playlistmarket.search.ui.KEY_HISTORY
import com.practicum.playlistmarket.search.ui.KEY_HISTORY_ALL


class SearchHistory(private val sharedPref: SharedPreferences){
    lateinit var listener: SharedPreferences.OnSharedPreferenceChangeListener

    private val historyAdapter = HistoryAdapter()

    companion object {
        private const val HISTORY_TRACK_MAX = 10
    }

    fun addTrackHistory(tracksHistory: ArrayList<Track>){
        historyAdapter.trackListHistory = tracksHistory
        tracksHistory.addAll(read(sharedPref))
        listener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPrefHistory, key ->
            if (key == KEY_HISTORY) {
                val track = sharedPrefHistory?.getString(key, null)
                if (track != null) {
                    repeatingTrackCheck(track, tracksHistory)
                    if (historyAdapter.trackListHistory.size >= HISTORY_TRACK_MAX) {
                        tracksHistory.removeAt(historyAdapter.trackListHistory.size - 1)
                    }
                    historyAdapter.trackListHistory.add(0, createTrackFromJson(track))
                    historyAdapter.notifyItemInserted(0)
                }
            }
        }
    }

    fun clearTrack(){
        sharedPref.edit().clear().apply()
        historyAdapter.trackListHistory.clear()
    }


    private fun createTrackFromJson(json: String?): Track {
        return Gson().fromJson(json, Track::class.java)
    }


    private fun read(sharedPreferences: SharedPreferences): Array<Track> {
        val json = sharedPreferences.getString(KEY_HISTORY_ALL, null) ?: return emptyArray()
        return Gson().fromJson(json, Array<Track>::class.java)
    }


    private fun repeatingTrackCheck(track: String, tracksHistory: ArrayList<Track>) {
        val iterator = tracksHistory.iterator()
        while (iterator.hasNext()) {
            val item = iterator.next()
            if (item.trackId == createTrackFromJson(track).trackId) {
                iterator.remove()
            }
        }
    }

}