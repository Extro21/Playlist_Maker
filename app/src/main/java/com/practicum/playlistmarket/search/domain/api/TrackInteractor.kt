package com.practicum.playlistmarket.search.domain.api

import com.practicum.playlistmarket.player.domain.models.Track

interface TrackInteractor {

    fun searchTrack(expression: String, consumer: TrackConsumer)

    interface TrackConsumer {
        fun consume(foundTrack: List<Track>, code : Int)
    }

}