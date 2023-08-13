package com.practicum.playlistmarket.util

import com.practicum.playlistmarket.player.domain.models.Track

fun interface TrackClickListener {
    fun onTrackClick(track: Track)
}