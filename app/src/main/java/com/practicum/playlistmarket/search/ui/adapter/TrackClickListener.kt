package com.practicum.playlistmarket.search.ui.adapter

import com.practicum.playlistmarket.player.domain.models.Track

fun interface TrackClickListener {
    fun onTrackClick(track: Track)
}