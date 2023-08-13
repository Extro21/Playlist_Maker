package com.practicum.playlistmarket.search.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.search.domain.api.SharedPreferensecHistory


const val HISTORY_TRACK = "history_track"
const val KEY_HISTORY = "key_history"
const val KEY_HISTORY_ALL = "key_history_all"

class SharedPreferencesHistoryImpl(private val context: Context) : SharedPreferensecHistory {

    private lateinit var sharedPreferences: SharedPreferences
    lateinit var searchHistory: SearchHistory

    private fun getSharedPreferences() {
        sharedPreferences = context.getSharedPreferences(
            HISTORY_TRACK,
            MODE_PRIVATE
        )
    }

    override fun addHistoryTracks(tracksHistory: ArrayList<Track>) {
        getSharedPreferences()

        searchHistory = SearchHistory(sharedPreferences)

        searchHistory.addTrackHistory(tracksHistory)

        sharedPreferences.registerOnSharedPreferenceChangeListener(searchHistory.listener)
    }

    override fun addTrackInAdapter(track: Track) {
        getSharedPreferences()

        val json = Gson().toJson(track)
        sharedPreferences.edit()
            .putString(KEY_HISTORY, json)
            .apply()
    }

    override fun editHistoryList(tracksHistory: ArrayList<Track>) {
        sharedPreferences.edit()
            .putString(KEY_HISTORY_ALL, Gson().toJson(tracksHistory))
            .apply()
    }

    override fun clearTrack(tracksHistory: ArrayList<Track>) {
        sharedPreferences.edit().clear().apply()
        tracksHistory.clear()
    }
}