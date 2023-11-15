package com.practicum.playlistmarket.media.ui.adapter

import com.practicum.playlistmarket.player.domain.models.Track

interface TrackClickListenerLong {

    fun onTrackClick(track: Track)

    fun onItemLongClick(trackId: String)

}