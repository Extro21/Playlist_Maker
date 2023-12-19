package com.practicum.playlistmarket.search.data.dto

import com.practicum.playlistmarket.media.data.db.AppDataBase
import com.practicum.playlistmarket.media.data.db.TrackDbConvertor
import com.practicum.playlistmarket.media.data.db.entity.TrackEntity
import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.search.data.SearchStatus
import com.practicum.playlistmarket.search.data.NetworkClient
import com.practicum.playlistmarket.search.domain.api.TrackRepository
import com.practicum.playlistmarket.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class TrackRepositoryImpl(
    private val networkClient: NetworkClient,
    private val dataBase: AppDataBase,
    private val dbConvertor: TrackDbConvertor
) : TrackRepository {

    var codeResult = 0

    override fun code(): Int {
        return codeResult
    }

    override fun searchTrack(expression: String): Flow<Resource<List<Track>>> = flow {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        when (response.resultCode) {
            -1 -> emit(Resource.Error(SearchStatus.NO_INTERNET.nameStatus))
            200 -> {
                val listFavoriteTrack: List<String> = dataBase.trackDao().getTrackFavoriteId()
                with(response as TrackSearchResponse) {
                    val data = results.map {
                        Track(
                            it.trackId,
                            it.artworkUrl100,
                            it.trackName,
                            it.artistName,
                            it.trackTimeMillis,
                            it.collectionName,
                            it.releaseDate,
                            it.primaryGenreName,
                            it.country,
                            it.previewUrl
                        )
                    }
                    checkFavoriteTrack(data, listFavoriteTrack)
                    emit(Resource.Success(data))
                }
            }
            else -> emit(Resource.Error(SearchStatus.NO_INTERNET.nameStatus))
        }

    }

    private fun checkFavoriteTrack(data: List<Track>, listFavoriteTrack: List<String>) {
        for (item1 in data) {
            for (item2 in listFavoriteTrack) {
                if (item1.trackId == item2) {
                    item1.isFavorite = true
                }
            }
        }
    }
}