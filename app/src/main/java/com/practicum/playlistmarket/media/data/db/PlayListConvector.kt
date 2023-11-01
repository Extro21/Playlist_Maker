package com.practicum.playlistmarket.media.data.db

import com.practicum.playlistmarket.media.data.db.entity.PlayListEntity
import com.practicum.playlistmarket.media.data.db.entity.TrackEntity
import com.practicum.playlistmarket.media.data.db.entity.TrackPlayListEntity
import com.practicum.playlistmarket.media.domain.module.PlayList
import com.practicum.playlistmarket.player.domain.models.Track

class PlayListConvector {

    fun map(playList: PlayList) : PlayListEntity {
        return PlayListEntity(
            playList.playListId,
            playList.name,
            playList.description,
            playList.uri,
            playList.idTracks,
            playList.quantityTracks,
        )
    }

    fun map(playList: PlayListEntity) : PlayList {
        return PlayList(
            playList.playListId,
            playList.name,
            playList.description,
            playList.uri,
            playList.idTracks,
            playList.quantityTracks,
        )
    }

    fun map(track: Track): TrackPlayListEntity {
        return TrackPlayListEntity(
            trackId = track.trackId,
            track.artworkUrl100,
            track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
            track.isFavorite
        )
    }

    fun map(track: TrackPlayListEntity): Track {
        return Track(
            trackId = track.trackId,
            track.artworkUrl100,
            track.trackName,
            track.artistName,
            track.trackTimeMillis,
            track.collectionName,
            track.releaseDate,
            track.primaryGenreName,
            track.country,
            track.previewUrl,
            track.isFavorite
        )
    }

}