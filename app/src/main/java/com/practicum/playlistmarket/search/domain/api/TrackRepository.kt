package com.practicum.playlistmarket.search.domain.api

import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.util.Resource
import kotlinx.coroutines.flow.Flow

interface TrackRepository {

        fun code() : Int

        fun searchTrack(expression : String): Flow<Resource<List<Track>>>

}