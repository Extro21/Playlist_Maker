package com.practicum.playlistmarket.search.domain.api

import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

interface TrackInteractor {

   // fun searchTrack(expression: String, consumer: TrackConsumer)
    fun searchTrack(expression: String) : Flow<Pair<List<Track>?, String?>>

//    interface TrackConsumer {
//        fun consume(foundTrack: List<Track>?, errorMessage : String?)
//    }

}