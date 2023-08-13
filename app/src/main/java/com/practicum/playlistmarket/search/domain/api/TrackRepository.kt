package com.practicum.playlistmarket.search.domain.api

import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.util.Resource

interface TrackRepository {

        fun code() : Int

        fun searchTrack(expression : String): Resource<List<Track>>

}