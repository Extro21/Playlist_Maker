package com.practicum.playlistmarket.media.domain.impl

import android.util.Log
import com.practicum.playlistmarket.media.domain.FavoriteListener
import com.practicum.playlistmarket.media.domain.db.FavoriteInteractor
import com.practicum.playlistmarket.media.domain.repository.FavoriteRepository
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow

class FavoriteInteractorImpl(private val favoriteRepository: FavoriteRepository) : FavoriteInteractor {

    override fun favoriteTracks(): Flow<List<Track>> {
        return favoriteRepository.favoriteTrack()
    }

    override suspend fun addTrackFavorite(track: Track) {
        Log.d("LikeLike", "${track.isFavorite} Interactor")
        favoriteRepository.addTrackFavorite(track)

    }

    override fun setListener(listener: FavoriteListener) {
        favoriteRepository.setupListener(listener)
    }

    override suspend fun checkLikeTrack(trackId: String) : Boolean {
      return favoriteRepository.checkLikeTrack(trackId)
    }
}