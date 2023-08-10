package com.practicum.playlistmarket.search.domain.api

import com.practicum.playlistmarket.player.domain.models.Track

interface TrackRepository {

        fun code() : Int

        fun searchTrack(expression : String): List<Track>

}