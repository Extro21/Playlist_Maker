package com.practicum.playlistmarket.search.data.dto

import com.practicum.playlistmarket.player.domain.models.Track
import com.practicum.playlistmarket.presentation.SearchStatus
import com.practicum.playlistmarket.search.data.NetworkClient
import com.practicum.playlistmarket.search.domain.api.TrackRepository
import com.practicum.playlistmarket.util.Resource

class TrackRepositoryImpl(private val networkClient: NetworkClient) : TrackRepository {

    var codeResult = 0

    override fun code(): Int {
        return codeResult
    }


//    override fun searchTrack(expression: String): List<Track> {
//        val response = networkClient.doRequest(TrackSearchRequest(expression))
//        codeResult = response.resultCode
//        if (codeResult == 200) {
//            return (response as TrackSearchResponse).results.map {
//                Track(it.trackId, it.artworkUrl100, it.trackName, it.artistName, it.trackTimeMillis
//                , it.collectionName, it.releaseDate, it.primaryGenreName, it.country, it.previewUrl)
//            }
//        } else {
//            return emptyList()
//        }
//    }

    override fun searchTrack(expression: String): Resource<List<Track>> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))
        return when (response.resultCode) {
            -1 -> Resource.Error(SearchStatus.NO_INTERNET.nameStatus)
            200 -> {
                Resource.Success((response as TrackSearchResponse).results.map {
                    Track(it.trackId, it.artworkUrl100, it.trackName, it.artistName, it.trackTimeMillis
               , it.collectionName, it.releaseDate, it.primaryGenreName, it.country, it.previewUrl)
                })
            }
            else -> Resource.Error(SearchStatus.NO_INTERNET.nameStatus)
        }

    }
}