package com.practicum.playlistmarket.search.domain.impl

import com.practicum.playlistmarket.search.domain.api.TrackInteractor
import com.practicum.playlistmarket.search.domain.api.TrackRepository
import java.util.concurrent.Executors

class TrackInteraktorImpl(private val repository: TrackRepository) : TrackInteractor {

   private val executor = Executors.newCachedThreadPool()

    override fun searchTrack(expression: String, consumer: TrackInteractor.TrackConsumer) {
        executor.execute{
            consumer.consume(repository.searchTrack(expression), repository.code())
        }
    }
}