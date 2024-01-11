package com.practicum.playlistmarket.media.domain.repository

import com.practicum.playlistmarket.media.domain.FavoriteListener
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow


interface FavoriteRepository {

    fun favoriteTrack(): Flow<List<Track>>

    suspend fun addTrackFavorite(track: Track)
    suspend fun deleteTrackFavorite(trackId: String)

    fun setupListener(listener: FavoriteListener)

    suspend fun checkLikeTrack(trackId: String) : Boolean


}