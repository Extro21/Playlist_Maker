package com.practicum.playlistmarket.media.domain.db

import com.practicum.playlistmarket.media.domain.FavoriteListener
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow


interface FavoriteInteractor {

    fun favoriteTracks(): Flow<List<Track>>
    suspend fun addTrackFavorite(track: Track)

    fun setListener(listener : FavoriteListener)

    suspend fun checkLikeTrack(trackId: String) : Boolean

}