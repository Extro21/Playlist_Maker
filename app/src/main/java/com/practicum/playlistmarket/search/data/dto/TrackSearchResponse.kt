package com.practicum.playlistmarket.search.data.dto

import com.practicum.playlistmarket.player.domain.models.Track

class TrackSearchResponse(
    val results: ArrayList<Track>
) : Response() {
}