package com.practicum.playlistmarket.media.data.repository

import android.util.Log
import com.practicum.playlistmarket.media.data.db.AppDataBase
import com.practicum.playlistmarket.media.data.db.TrackDbConvertor
import com.practicum.playlistmarket.media.data.db.entity.TrackEntity
import com.practicum.playlistmarket.media.domain.FavoriteListener
import com.practicum.playlistmarket.media.domain.repository.FavoriteRepository
import com.practicum.playlistmarket.player.domain.api.PlayerListener
import com.practicum.playlistmarket.player.domain.models.Track
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FavoriteRepositoryImpl(
    private val dataBase: AppDataBase,
    private val trackDbConvertor: TrackDbConvertor,
) : FavoriteRepository {

    private var listener: FavoriteListener? = null
    override suspend fun addTrackFavorite(track: Track) {
        Log.e("LikeLike", "${track.isFavorite} Repo")
        if (track.isFavorite) {
            track.isFavorite = false
            dataBase.trackDao().deleteTrackFavorite(track.trackId)
            listener?.onFavoriteUpdate(track.isFavorite)
          //  Log.e("LikeLike", "${track.isFavorite.toString()} delete ")
        } else {
            track.isFavorite = true
            dataBase.trackDao().insertTrackFavorite(track = trackConvectorEntity(track))
            listener?.onFavoriteUpdate(track.isFavorite)
          //  Log.e("LikeLike", "${track.isFavorite.toString()} add ")
        }
    }

    override suspend fun checkLikeTrack(trackId: String) : Boolean {
        val existsTrack = dataBase.trackDao().doesTrackExist(trackId)
        Log.e("CheckTrackSQL", existsTrack.toString())
        return existsTrack
    }

    override fun setupListener(listener: FavoriteListener) {
        this.listener = listener
    }

    override suspend fun deleteTrackFavorite(trackId: String) {
        dataBase.trackDao().deleteTrackFavorite(trackId)
    }

    override fun favoriteTrack(): Flow<List<Track>> = flow {
        val track = dataBase.trackDao().getTrackFavorite()
        emit(trackConvector(track))
    }

    private fun trackConvector(track: List<TrackEntity>): List<Track> {
        return track.map { track -> trackDbConvertor.map(track) }
    }

    private fun trackConvectorEntity(track: Track): TrackEntity {
        return trackDbConvertor.map(track)
    }


}