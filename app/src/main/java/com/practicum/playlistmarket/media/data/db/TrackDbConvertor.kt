package com.practicum.playlistmarket.media.data.db

import com.practicum.playlistmarket.media.data.db.entity.TrackEntity
import com.practicum.playlistmarket.player.domain.models.Track

class TrackDbConvertor {


    fun map(track: TrackEntity): Track {
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

    fun map(track: Track): TrackEntity {
        return TrackEntity(
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