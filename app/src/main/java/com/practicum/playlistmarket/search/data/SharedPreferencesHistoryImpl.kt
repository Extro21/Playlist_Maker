package com.practicum.playlistmarket.search.data

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.google.gson.Gson
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.search.domain.api.SharedPreferensecHistory


const val HISTORY_TRACK = "history_track"
const val KEY_HISTORY_ALL = "key_history_all"

class SharedPreferencesHistoryImpl(private val context: Context) : SharedPreferensecHistory {

    private lateinit var sharedPreferences: SharedPreferences
    private var tracksHistory = mutableListOf<Track>()

    override fun getAllTracks(): List<Track> {
        getSharedPreferences()
        tracksHistory = read()
        return tracksHistory
    }

    private fun getSharedPreferences() {
        sharedPreferences = context.getSharedPreferences(
            HISTORY_TRACK,
            MODE_PRIVATE
        )
    }

    private fun addHistoryTracks(newTrack: Track) {
        tracksHistory = read()
        if (tracksHistory.contains(newTrack)) tracksHistory.remove(newTrack)
        if (tracksHistory.size == HISTORY_TRACK_MAX) {
            tracksHistory.removeLast()
        }
        tracksHistory.add(0, newTrack)
        writeHistoryToJson(tracksHistory)
    }

    override fun saveTrack(track: Track) {
        addHistoryTracks(track)
    }

    private fun writeHistoryToJson(searchTracksHistory: List<Track>) {
        val json = Gson().toJson(searchTracksHistory)
        sharedPreferences.edit().putString(KEY_HISTORY_ALL, json).apply()
    }

    override fun editHistoryList() {
        sharedPreferences.edit()
            .putString(KEY_HISTORY_ALL, Gson().toJson(tracksHistory))
            .apply()
    }

    override fun clearTrack() {
        sharedPreferences.edit().clear().apply()
        tracksHistory.clear()
    }

    companion object {
        private const val HISTORY_TRACK_MAX = 10
    }

    private fun read(): MutableList<Track> {
        val json = sharedPreferences.getString(KEY_HISTORY_ALL, null) ?: return mutableListOf()
        return Gson().fromJson(json, Array<Track>::class.java).toMutableList()
    }


}