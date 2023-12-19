package com.practicum.playlistmarket.search.domain.impl

import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.search.domain.api.TrackInteractor
import com.practicum.playlistmarket.search.domain.api.TrackRepository
import com.practicum.playlistmarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TrackInteraktorImpl(private val repository: TrackRepository) : TrackInteractor {

    override fun searchTrack(expression: String): Flow<Pair<List<Track>?, String?>> {
        return repository.searchTrack(expression).map { result ->
            when (result) {
                is Resource.Success -> Pair(result.data, null)
                is Resource.Error -> Pair(null, result.message)
            }
        }
    }

}